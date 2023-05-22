package com.service.category.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "section")
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class SectionEntity {

    @Id
    private BigInteger id;

    private String publicId;

    private String name;

    @Builder.Default
    private Set<String> categories = new HashSet<>();    //category publicId
}
