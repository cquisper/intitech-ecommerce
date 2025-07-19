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
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service @Slf4j
@RequiredArgsConstructor
public class UploadService {

    private final Cloudinary cloudinary;

    public Mono<UploadResponse> uploadImage(FilePart filePart){
        return Mono.defer(() -> {
            if (Objects.isNull(filePart)) return Mono.just(ObjectUtils.emptyMap());

            if (filePart.filename().isBlank()) return Mono.just(ObjectUtils.emptyMap());

            return convertToFile(filePart)
                    .handle((file, sink) -> {
                        try {
                            sink.next(cloudinary.uploader().upload(file, ObjectUtils.emptyMap()));
                        } catch (IOException e) {
                            sink.error(new RuntimeException(e));
                        } finally {
                            file.deleteOnExit();
                        }
                    });
        }).map(this::mapToDto)
                .doOnSuccess(uploadResponse -> log.info("Image upload: {}", uploadResponse));
    }

    public Mono<UploadResponse> deleteImage(String publicId){
        return Mono.fromCallable(() -> cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap()))
                .map(this::mapToDto)
                .doOnSuccess(uploadResponse -> log.info("Image delete: {}", uploadResponse));
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

        File file = new File(UUID.randomUUID() + filePart.filename());

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
