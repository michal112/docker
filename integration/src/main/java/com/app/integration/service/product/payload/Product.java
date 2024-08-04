package com.app.integration.service.product.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private String publicId;

    private String name;

    private Integer price;

    private LocalDatePayload productionDate;

    private String categoryPublicId;

    private String sectionPublicId;

    //enrich
    private String categoryName;

    public Product() {
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDatePayload getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDatePayload productionDate) {
        this.productionDate = productionDate;
    }

    public String getCategoryPublicId() {
        return categoryPublicId;
    }

    public void setCategoryPublicId(String categoryPublicId) {
        this.categoryPublicId = categoryPublicId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSectionPublicId() {
        return sectionPublicId;
    }

    public void setSectionPublicId(String sectionPublicId) {
        this.sectionPublicId = sectionPublicId;
    }
}