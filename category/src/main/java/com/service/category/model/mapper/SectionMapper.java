package com.service.category.model.mapper;

import com.service.category.model.entity.CategoryEntity;
import com.service.category.model.entity.SectionEntity;
import com.service.category.model.payload.SectionPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SectionMapper {

    private final CategoryMapper categoryMapper;

    private final PublicIdGenerator publicIdGenerator;

    public SectionPayload toPayload(SectionEntity sectionEntity, List<CategoryEntity> categories) {
        return new SectionPayload(sectionEntity.getPublicId(), sectionEntity.getName(),
                categories.stream()
                        .map(categoryMapper::toPayload)
                        .collect(Collectors.toList()));
    }

    public SectionEntity toEntity(SectionPayload sectionPayload) {
        return SectionEntity.builder()
                .publicId(publicIdGenerator.getPublicId())
                .name(sectionPayload.name())
                .build();
    }
}
