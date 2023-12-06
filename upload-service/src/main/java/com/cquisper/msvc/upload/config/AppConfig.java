package com.cquisper.msvc.upload.config;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final Environment env;
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(Map.of(
                "cloud_name", env.getProperty("cloudinary.cloudName", ""),
                "api_key", env.getProperty("cloudinary.apiKey", ""),
                "api_secret", env.getProperty("cloudinary.apiSecret", ""))
        );
    }
}
