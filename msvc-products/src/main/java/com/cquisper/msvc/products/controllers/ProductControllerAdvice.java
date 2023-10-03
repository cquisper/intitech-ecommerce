package com.cquisper.msvc.products.controllers;

import com.cquisper.msvc.products.exception.ResourceDuplicateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice @Slf4j
public class ProductControllerAdvice {

    @ExceptionHandler(ResourceDuplicateException.class)
    public ResponseEntity<?> handleException(ResourceDuplicateException ex){
        log.error(ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(Map.of("error:", ex.getMessage()));
    }
}
