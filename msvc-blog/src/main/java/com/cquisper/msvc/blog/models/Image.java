package com.cquisper.msvc.blog.models;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class Image {
    private String publicId;

    private String url;
}
