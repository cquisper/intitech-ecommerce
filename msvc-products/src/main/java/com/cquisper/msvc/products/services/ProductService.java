package com.cquisper.msvc.products.services;

import com.cquisper.msvc.products.client.InventoryWebClient;
import com.cquisper.msvc.products.client.RatingWebClient;
import com.cquisper.msvc.products.client.UserWebClient;
import com.cquisper.msvc.products.dto.ProductRequest;
import com.cquisper.msvc.products.dto.ProductResponse;
import com.cquisper.msvc.products.dto.UserResponse;
import com.cquisper.msvc.products.exception.ResourceDuplicateException;
import com.cquisper.msvc.products.models.Inventory;
import com.cquisper.msvc.products.models.User;
import com.cquisper.msvc.products.models.entities.Product;
import com.cquisper.msvc.products.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    private final RatingWebClient ratingWebClient;

    private final UserWebClient userWebClient;

    private final InventoryWebClient inventoryClient;

    public Flux<ProductResponse> getAllProducts(){
        return this.productRepository.findAll()
                .flatMap(this::entityToDto);
    }

    public Mono<ProductResponse> getProductById(String id){
        return this.productRepository.findById(id)
                .flatMap(this::entityToDto);
    }

    public Mono<ProductResponse> createProduct(ProductRequest productRequest){
        return Mono.just(dtoToEntity(productRequest))
                .doOnNext(product -> product.setCreatedAt(LocalDateTime.now()))
                .flatMap(product -> {
                    String sku = product.getName().replaceAll("\\s+", "-").toLowerCase();
                    return this.productRepository.existsBySku(sku)
                            .flatMap(exists -> {
                                if (exists){
                                    return Mono.error(new ResourceDuplicateException("Product with sku %s already exists".formatted(sku)));
                                }
                                product.setSku(sku);
                                return this.productRepository.insert(product)
                                        .flatMap(productInsert -> this.inventoryClient.createInventory(
                                                Inventory.builder()
                                                        .productId(productInsert.getId())
                                                        .quantity(productRequest.quantity())
                                                        .build()
                                                )
                                                .thenReturn(productInsert));
                            });
                })
                .flatMap(this::entityToDto)
                .doOnSuccess(product -> log.info("Product created: {}", product));
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
                .flatMap(this::entityToDto)
                .doOnSuccess(product -> log.info("Product updated: {}", product));
    }

    public Mono<Void> deleteProduct(String id){
        return this.productRepository.deleteById(id)
                .doOnTerminate(() -> log.info("Product deleted: {}", id));
    }

    public Flux<ProductResponse> getAllProductsFilter(Map<String, String> params){
        String name = params.getOrDefault("name", "");
        String brand = params.getOrDefault("brand", "");
        String category = params.getOrDefault("category", "");

        Sort sort;
        Pageable pageable = Pageable.unpaged();

        if (params.containsKey("sort")){
            String[] sortParams = params.get("sort").split(",");
            sort = Sort.by(sortParams);
        }else{
            sort = Sort.by("createdAt").ascending();
        }

        if (params.containsKey("page") && params.containsKey("limit")) {
            int page = Integer.parseInt(params.get("page"));
            int limit = Integer.parseInt(params.get("limit"));
            pageable = PageRequest.of(page - 1, limit, sort);
        } else if (pageable.isUnpaged()){
            return this.productRepository.findAllByNameLikeIgnoreCaseAndBrandLikeIgnoreCaseAndCategoryLikeIgnoreCase(name, brand, category, sort)
                    .flatMap(this::entityToDto);
        }

        return this.productRepository.findAllByNameLikeIgnoreCaseAndBrandLikeIgnoreCaseAndCategoryLikeIgnoreCase(name, brand, category, pageable)
                .flatMap(this::entityToDto);
    }

    public Mono<UserResponse> addProductToWishlist(String email, String idProduct){
        return this.productRepository.findById(idProduct)
                .flatMap(product -> this.userWebClient.getUserByEmail(email)
                        .flatMap(user -> {
                            if (!user.getWishList().add(product.getId())) {
                                user.getWishList().remove(product.getId());
                            }
                            return this.userWebClient.updateUser(
                                    Map.of("wishList", user.getWishList()),
                                    user.getId()
                            );
                        })
                )
                .flatMap(userToWishListAndRating())
                .doOnSuccess(user -> log.info("Product added to wishlist: {}", user));
    }


    public Mono<UserResponse> getWishListUser(String email){
        return this.userWebClient.getUserByEmail(email)
                .flatMap(userToWishListAndRating());
    }

    private Function<User, Mono<? extends UserResponse>> userToWishListAndRating() {
        return user -> this.productRepository.findAllById(user.getWishList())
                .flatMap(product -> this.ratingWebClient.getAllRatingByIdProduct(product.getId())
                        .collectList()
                        .doOnNext(product::setRatings)
                        .thenReturn(product))
                .flatMap(this::entityToDto)
                .collectList()
                .map(products -> UserResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .wishList(products)
                        .build()
                );
    }

    public static Product dtoToEntity(ProductRequest productRequest){
        return Product.builder()
                .name(productRequest.name())
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

    public Mono<ProductResponse> entityToDto(Product product){
        return Mono.just(ProductResponse.builder()
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
                .totalRating(product.getTotalRating())
                .createdAt(product.getCreatedAt()))
                .flatMap(productBuild -> this.inventoryClient.findByProductId(product.getId())
                        .flatMap(inventory -> {
                            productBuild.quantity(inventory.getQuantity());
                            productBuild.sold(inventory.getSold());
                            return this.ratingWebClient.getAllRatingByIdProduct(product.getId())
                                    .collectList()
                                    .map(ratings -> {
                                        productBuild.ratings(ratings);
                                        return productBuild.build();
                                    });
                        })
                )
                .doOnSuccess(productResponse -> log.info("Product found: {}", productResponse));
    }
}