package com.cquisper.msvc.products.controllers;

import com.cquisper.msvc.products.models.entities.ProdCategory;
import com.cquisper.msvc.products.services.ProdCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class ProdCategoryController {

    private final ProdCategoryService prodCategoryService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProdCategory> getAllProdCategories(){
        return this.prodCategoryService.getAllProdCategories();
    }

    @GetMapping("/find-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProdCategory> getProdCategoryById(@PathVariable String id){
        return this.prodCategoryService.getProdCategoryById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProdCategory> createProdCategory(@RequestBody ProdCategory prodCategory){
        return this.prodCategoryService.createProdCategory(prodCategory);
    }

    @PatchMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProdCategory> updateProdCategory(@RequestBody ProdCategory prodCategory, @PathVariable String id){
        return this.prodCategoryService.updateProdCateroy(prodCategory, id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProdCategoryById(@PathVariable String id){
        return this.prodCategoryService.deleteProdCategoryById(id);
    }

}
