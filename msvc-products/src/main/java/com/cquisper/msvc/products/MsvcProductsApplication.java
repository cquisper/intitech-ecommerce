package com.cquisper.msvc.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class MsvcProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcProductsApplication.class, args);
    }
}
