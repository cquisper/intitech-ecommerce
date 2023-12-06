package com.cquisper.msvc.enquire.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "enquiries")
@Data @Builder
public class Enquire {
    @Id
    private String id;

    private String name;

    private String mobile;

    private String comment;

    private String email;

    private String status;
}