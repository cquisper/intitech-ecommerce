package com.cquisper.msvc.products.models.entities;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class Image {
    private String publicId;

    private String url;
}
