package com.cquisper.msvc.products.services;

import com.cquisper.msvc.products.models.entities.ProdCategory;
import com.cquisper.msvc.products.repositories.ProdCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service @Slf4j
@RequiredArgsConstructor
public class ProdCategoryService {

    private final ProdCategoryRepository prodCategoryRepository;

    public Mono<ProdCategory> createProdCategory(ProdCategory prodCategory){
        return this.prodCategoryRepository.existsByNameIgnoreCase(prodCategory.getName())
                .flatMap(exists -> {
                    if(exists){
                        return Mono.error(new Exception("Category already exists"));
                    }
                    return this.prodCategoryRepository.save(prodCategory);
                })
                .doOnSuccess(prodCategory1 -> log.info("Created category: {}", prodCategory1.getName()));
    }

    public Mono<ProdCategory> updateProdCateroy(ProdCategory prodCategory, String id){
        return this.prodCategoryRepository.findById(id)
                .flatMap(prodCategoryC -> {
                    if (prodCategory.getName().equals(prodCategoryC.getName())){
                        return Mono.empty();
                    }
                    return this.prodCategoryRepository.existsByNameIgnoreCase(prodCategory.getName())
                            .flatMap(exists -> {
                                if(exists){
                                    return Mono.error(new Exception("Category already exists"));
                                }
                                prodCategoryC.setName(prodCategory.getName());
                                return this.prodCategoryRepository.save(prodCategoryC);
                            });
                })
                .doOnSuccess(prodCategory1 -> log.info("Updated category: {}", prodCategory1.getName()));
    }

    public Flux<ProdCategory> getAllProdCategories(){
        return this.prodCategoryRepository.findAll();
    }

    public Mono<ProdCategory> getProdCategoryById(String id){
        return this.prodCategoryRepository.findById(id)
                .doOnSuccess(prodCategory -> log.info("Get category: {}", prodCategory.getName()));
    }

    public Mono<Void> deleteProdCategoryById(String id){
        return this.prodCategoryRepository.deleteById(id)
                .doOnTerminate(() -> log.info("Deleted category with id: {}", id));
    }
}
