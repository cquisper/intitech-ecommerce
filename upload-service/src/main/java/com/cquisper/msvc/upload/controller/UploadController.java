package com.cquisper.msvc.upload.controller;

import com.cquisper.msvc.upload.dto.UploadResponse;
import com.cquisper.msvc.upload.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping(path = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<UploadResponse> uploadImage(@RequestPart Flux<FilePart> images){
        return images
                .flatMap(uploadService::uploadImage)
                .onErrorResume(error -> Flux.error(new IllegalArgumentException(error.getMessage())));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UploadResponse> deleteImage(@PathVariable String id){
        return uploadService.deleteImage(id);
    }

    @DeleteMapping("/delete-name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Boolean> deleteByName(@PathVariable String name){
        return uploadService.deleteByName(name);
    }

}
