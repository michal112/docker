package com.service.category.model.mapper;

import com.service.category.model.entity.CategoryEntity;
import com.service.category.model.payload.CategoryPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final PublicIdGenerator publicIdGenerator;

    public CategoryPayload toPayload(CategoryEntity categoryEntity) {
        return new CategoryPayload(categoryEntity.getPublicId(), categoryEntity.getName());
    }

    public CategoryEntity toEntity(CategoryPayload categoryPayload) {
        return CategoryEntity.builder()
                .publicId(publicIdGenerator.getPublicId())
                .name(categoryPayload.name())
                .build();
    }
}
