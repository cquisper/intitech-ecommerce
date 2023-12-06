package com.cquisper.msvc.products.repositories;

import com.cquisper.msvc.products.models.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Mono<Boolean> existsBySku(String sku);
    //Flux<Product> findAllById(List<String> ids);

    @Query("{ 'precio': { $gte: ?1 }, 'precio': { $lte: ?2 } }")
    Flux<Product> findAllByPriceBetween(BigDecimal price1, BigDecimal price2);

    //@Query("{ 'precio': { $gte: ?1 } }")
    Flux<Product> findAllByPriceGreaterThanEqual(BigDecimal price1);

    Flux<Product> findAllByNameLikeIgnoreCaseAndBrandLikeIgnoreCaseAndCategoryLikeIgnoreCase(String name,
                                                                                             String brand,
                                                                                             String category,
                                                                                             Sort sort);

    Flux<Product> findAllByNameLikeIgnoreCaseAndBrandLikeIgnoreCaseAndCategoryLikeIgnoreCase(String name,
                                                                                             String brand,
                                                                                             String category,
                                                                                             Pageable pageable);
    Flux<Product>findAllByCategoryIgnoreCase(String Category);
}
