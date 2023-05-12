package com.service.category.model.mapper;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PublicIdGenerator {

    public String getPublicId() {
        return UUID.randomUUID().toString();
    }
}
