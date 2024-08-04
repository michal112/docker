package com.app.integration.service.section.payload;

import com.app.integration.service.category.payload.Category;

import java.util.List;

public class Section {

    private String publicId;

    private String name;

    private List<Category> categories;

    public Section() {
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
