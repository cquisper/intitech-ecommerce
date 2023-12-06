package com.cquisper.msvc.upload.dto;

import lombok.Builder;

@Builder
public record UploadResponse(
        String originalFilename,
        String url,
        String publicId
) {
}
