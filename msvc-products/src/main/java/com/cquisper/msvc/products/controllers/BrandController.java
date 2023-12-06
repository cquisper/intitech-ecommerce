package com.cquisper.msvc.products.controllers;

import com.cquisper.msvc.products.models.entities.Brand;
import com.cquisper.msvc.products.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Brand> getAllBrands(){
        return this.brandService.getAllBrands();
    }

    @GetMapping("/find-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Brand> getBrandById(@PathVariable String id){
        return this.brandService.getBrandById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Brand> createBrand(@RequestBody Brand brand){
        return this.brandService.createBrand(brand);
    }

    @PatchMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Brand> updateBrand(@RequestBody Brand brand, @PathVariable String id){
        return this.brandService.updateBrand(brand, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBrand(@PathVariable String id){
        return this.brandService.deleteBrand(id);
    }
}
