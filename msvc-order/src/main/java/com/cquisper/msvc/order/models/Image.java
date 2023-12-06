package com.cquisper.msvc.order.models;

import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;

@Builder @Data
public class Image {
    private String publicId;

    private String url;

    @ConstructorProperties({"publicId", "url"})
    public Image(String publicId, String url) {
        this.publicId = publicId;
        this.url = url;
    }
}
