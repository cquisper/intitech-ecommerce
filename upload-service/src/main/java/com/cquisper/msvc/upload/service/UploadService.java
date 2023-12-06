package com.cquisper.msvc.upload.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cquisper.msvc.upload.dto.UploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service @Slf4j
@RequiredArgsConstructor
public class UploadService {

    private final Cloudinary cloudinary;

    private static final String SERVICE_IMG = "upload-service/img/";

    public Mono<Boolean> init(){
        return Mono.fromCallable(() -> {
            File uploadDir = new File(SERVICE_IMG);
            return uploadDir.mkdir();
        });
    }

    public Mono<UploadResponse> uploadImage(FilePart filePart){
        return convertToFile(filePart)
                .<Map<?,?>>handle((file, sink) -> {
                    try {
                        sink.next(cloudinary.uploader().upload(file, ObjectUtils.emptyMap()));
                    } catch (IOException e) {
                        sink.error(new RuntimeException(e));
                    }
                })
                .map(this::mapToDto)
                .doOnSuccess(uploadResponse -> log.info("Image upload: {}", uploadResponse));
    }

    public Mono<UploadResponse> deleteImage(String publicId){
        return Mono.fromCallable(() -> cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap()))
                .map(this::mapToDto)
                .doOnSuccess(uploadResponse -> log.info("Image delete: {}", uploadResponse));
    }

    public Mono<Boolean> deleteByName(String name){
        return Mono.fromCallable(() -> {
            File file = new File(SERVICE_IMG + name);
            if(file.exists() && file.canRead()){
                log.info("Image delete: {}", file.getName());
                return file.delete();
            }
            log.info("Image not found: {}", file.getName());
            return false;
        });
    }

    private Mono<File> convertToFile(FilePart filePart) {
        if(!Objects.requireNonNull(filePart.headers().getContentType()).toString().startsWith("image")){
            throw new IllegalArgumentException("Only images are allowed");
        }
        if (filePart.filename().contains("..")){
            throw new IllegalArgumentException("File name incorrect");
        }
        if (filePart.headers().getContentLength() > 1_000_000) { // 1 MB = 1,000,000 bytes
            throw new IllegalArgumentException("File size exceeds limit");
        }
        File file = new File(SERVICE_IMG + filePart.filename());
        return filePart.transferTo(file)
                .thenReturn(file);
    }

    private UploadResponse mapToDto(Map<?,?> result){
        return UploadResponse.builder()
                .originalFilename((String)result.get("original_filename"))
                .url((String)result.get("url"))
                .publicId((String)result.get("public_id"))
                .build();
    }
}
