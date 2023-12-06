package com.cquisper.msvc.products.services;

import com.cquisper.msvc.products.models.entities.Color;
import com.cquisper.msvc.products.repositories.ColorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service @Slf4j
@RequiredArgsConstructor
public class ColorService {

    private final ColorRepository colorRepository;

    public Flux<Color> getAllColors(){
        return this.colorRepository.findAll();
    }

    public Mono<Color> createColor(Color color){
        return this.colorRepository.existsByNameIgnoreCase(color.getName())
                .flatMap(exists -> {
                    if(exists){
                        return Mono.error(new Exception("Color already exists"));
                    }
                    return this.colorRepository.save(color);
                })
                .doOnSuccess(colorC -> log.info("Color created: {}", colorC));
    }

    public Mono<Color> getColorById(String id){
        return this.colorRepository.findById(id)
                .doOnSuccess(colorC -> log.info("Color found: {}", colorC));
    }

    public Mono<Color> updateColor(Color color, String id){
        return this.colorRepository.findById(id)
                .flatMap(colorC -> {
                    if(colorC.getName().equals(color.getName())){
                        return Mono.empty();
                    }
                    return this.colorRepository.existsByNameIgnoreCase(color.getName())
                            .flatMap(exists -> {
                                if(exists){
                                    return Mono.error(new Exception("Color already exists"));
                                }
                                colorC.setName(color.getName());
                                return this.colorRepository.save(colorC);
                            });
                })
                .doOnSuccess(colorC -> log.info("Color updated: {}", colorC));
    }

    public Mono<Void> deleteColor(String id){
        return this.colorRepository.deleteById(id)
                .doOnTerminate(() -> log.info("Color deleted: {}", id));
    }
}
