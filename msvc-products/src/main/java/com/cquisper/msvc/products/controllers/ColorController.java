package com.cquisper.msvc.products.controllers;

import com.cquisper.msvc.products.models.entities.Color;
import com.cquisper.msvc.products.services.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/color")
@RequiredArgsConstructor
public class ColorController {

    private final ColorService colorService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Color> getAllColors(){
        return this.colorService.getAllColors();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Color> createColor(@RequestBody Color color){
        return this.colorService.createColor(color);
    }

    @GetMapping("/find-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Color> getColorById(@PathVariable String id){
        return this.colorService.getColorById(id);
    }

    @PatchMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Color> updateColor(@RequestBody Color color, @PathVariable String id){
        return this.colorService.updateColor(color, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteColor(@PathVariable String id){
        return this.colorService.deleteColor(id);
    }
}
