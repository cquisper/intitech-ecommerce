package com.cquisper.msvc.products.repositories;

import com.cquisper.msvc.products.models.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Mono<Boolean> existsBySku(String sku);
    //Flux<Product> findAllById(List<String> ids);

    //@Query("{ 'name' : { $regex: ?0 }, 'brand' : { $regex: ?1 }, 'tags' : { $regex: ?2 }, 'price': { $gte: ?3, $lte: ?4 } }")
    @Query("{ 'price': { $gte: ?0, $lte: ?1 } }")
    Flux<Product> findAllByPriceBetweenSs(Double price1, Double price2);

    //@Query("{ 'precio': { $gte: ?1 } }")
    @Query("{'name' : { $regex: ?0 }, 'brand': { $regex: ?1 }, 'tags' : { $regex: ?2 }, 'category': { $regex: ?3 }, 'price': { $gte: ?4, $lte: ?5 } }")
    Flux<Product> findProductsByCriteria(String name, String brand, String tags, String category,
                                         Double priceGte, Double priceLte, Sort sort);

    @Query("{'name' : { $regex: ?0 }, 'brand': { $regex: ?1 }, 'tags' : { $regex: ?2 }, 'category': { $regex: ?3 }, 'price': { $gte: ?4, $lte: ?5 } }")
    Flux<Product> findProductsByCriteria(String name, String brand, String tags, String category,
                                         Double priceGte, Double priceLte, Pageable pageable);


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
