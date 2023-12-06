package com.cquisper.msvc.products.services;

import com.cquisper.msvc.products.models.entities.Brand;
import com.cquisper.msvc.products.repositories.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service @Slf4j
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public Mono<Brand> createBrand(Brand brand) {
        return this.brandRepository.existsByNameIgnoreCase(brand.getName())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new Exception("Brand already exists"));
                    }
                    return this.brandRepository.save(brand);
                })
                .doOnSuccess(brandC -> log.info("Brand created: {}", brandC));
    }

    public Mono<Brand> updateBrand(Brand brand, String id) {
        return this.brandRepository.findById(id)
                .flatMap(brandC -> {
                    if (brandC.getName().equals(brand.getName())) {
                        return Mono.empty();
                    }
                    return this.brandRepository.existsByNameIgnoreCase(brand.getName())
                            .flatMap(exists -> {
                                if (exists) {
                                    return Mono.error(new Exception("Brand already exists"));
                                }
                                brandC.setName(brand.getName());
                                return this.brandRepository.save(brandC);
                            });
                })
                .doOnSuccess(brandC -> log.info("Brand updated: {}", brandC));
    }

    public Flux<Brand> getAllBrands() {
        return this.brandRepository.findAll();
    }

    public Mono<Brand> getBrandById(String id) {
        return this.brandRepository.findById(id)
                .doOnSuccess(brandC -> log.info("Brand found: {}", brandC));
    }

    public Mono<Void> deleteBrand(String id){
        return this.brandRepository.deleteById(id)
                .doOnTerminate(() -> log.info("Brand deleted: {}", id));
    }
}
