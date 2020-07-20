package com.service.category.model.payload;

public class CategoryPayload {

    private final String publicId;

    private final String name;

    public CategoryPayload(String publicId, String name) {
        this.publicId = publicId;
        this.name = name;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getName() {
        return name;
    }
}
