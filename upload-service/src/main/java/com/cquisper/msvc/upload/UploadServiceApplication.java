package com.cquisper.msvc.upload;

import com.cquisper.msvc.upload.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor @Slf4j
public class UploadServiceApplication {

    private final UploadService uploadService;

    public static void main(String[] args) {
        SpringApplication.run(UploadServiceApplication.class, args);
    }


    @Bean
    public CommandLineRunner init() {
        return args -> {
            uploadService.init().subscribe(isCreated -> {
                if (isCreated) {
                    log.info("Image folder created");
                } else {
                    log.info("Image folder already exists");
                }
            });
        };
    }
}