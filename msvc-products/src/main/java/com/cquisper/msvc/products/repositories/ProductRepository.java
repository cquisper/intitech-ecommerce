package com.cquisper.msvc.products.repositories;

import com.cquisper.msvc.products.models.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
