package com.service.category.model.mapper;

import com.service.category.model.entity.CategoryEntity;
import com.service.category.model.payload.CategoryPayload;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryPayload toPayload(CategoryEntity categoryEntity) {
        return new CategoryPayload(categoryEntity.getPublicId(), categoryEntity.getName());
    }
}
