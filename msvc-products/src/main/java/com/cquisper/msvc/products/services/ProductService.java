package com.cquisper.msvc.products.services;

import com.cquisper.msvc.products.client.RatingWebClient;
import com.cquisper.msvc.products.dto.ProductRequest;
import com.cquisper.msvc.products.dto.ProductResponse;
import com.cquisper.msvc.products.exception.ResourceDuplicateException;
import com.cquisper.msvc.products.models.entities.Product;
import com.cquisper.msvc.products.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    private final RatingWebClient ratingWebClient;

    public Flux<ProductResponse> getAllProducts(){
        return this.productRepository.findAll()
                .map(ProductService::entityToDto);
    }

    public Flux<ProductResponse> getAllProductsWithRating(){
        return this.productRepository.findAll()
                .flatMap(product -> this.ratingWebClient.getAllRatingByIdProduct(product.getId())
                        .collectList()
                        .doOnNext(product::setRatings)
                        .thenReturn(product))
                .map(ProductService::entityToDto);
    }

    public Mono<ProductResponse> getProductById(String id){
        return this.productRepository.findById(id)
                .map(ProductService::entityToDto);
    }

    public Mono<ProductResponse> createProduct(ProductRequest productRequest){
        return Mono.just(productRequest)
                .flatMap(product -> this.productRepository.existsBySku(product.sku())
                        .flatMap(exists -> {
                            if (exists) {
                                return Mono.error(new ResourceDuplicateException(String.format(
                                        "Product with sku %s already exists",
                                        product.sku())));
                            }
                            return this.productRepository.insert(dtoToEntity(product));
                        })
                )
                .map(ProductService::entityToDto);
    }

    public Mono<ProductResponse> updateProduct(Map<Object, Object> fields, String id){
        return this.productRepository.findById(id)
                .doOnNext(product -> {
                    fields.forEach((key, value) -> {
                        Field field = ReflectionUtils.findRequiredField(Product.class, key.toString());
                        field.setAccessible(true);
                        log.info("Modification: " + field.getName());
                        if (field.getType().equals(BigDecimal.class)){
                            BigDecimal valueDecimal = BigDecimal.valueOf((Double) value);
                            ReflectionUtils.setField(field, product, valueDecimal);
                        }else {
                            ReflectionUtils.setField(field, product, value);
                        }
                    });
                })
                .flatMap(productRepository::save)
                .map(ProductService::entityToDto);
    }

    public Mono<Void> deleteProduct(String id){
        log.info("Deleting product with {} ", id);
        return this.productRepository.deleteById(id);
    }

    public static Product dtoToEntity(ProductRequest productRequest){
        return Product.builder()
                .name(productRequest.name())
                .sku(productRequest.sku())
                .description(productRequest.description())
                .category(productRequest.category())
                .price(productRequest.price())
                .colors(productRequest.colors())
                .brand(productRequest.brand())
                .tags(productRequest.tags())
                .images(productRequest.images())
                .totalRating(BigDecimal.ZERO)
                .build();
    }

    public static ProductResponse entityToDto(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .colors(product.getColors())
                .brand(product.getBrand())
                .tags(product.getTags())
                .images(product.getImages())
                .ratings(Objects.isNull(product.getRatings()) ? List.of() : product.getRatings())
                .totalRating(product.getTotalRating())
                .build();
    }
}