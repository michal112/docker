package com.service.category.service;

import com.service.category.exception.EntityNotFoundException;
import com.service.category.model.entity.CategoryEntity;
import com.service.category.model.mapper.CategoryMapper;
import com.service.category.model.payload.CategoryPayload;
import com.service.category.model.repository.CategoryReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryMapper categoryMapper;

    private final CategoryReactiveRepository categoryRepository;

    public Mono<CategoryPayload> create(CategoryPayload categoryPayload) {
        var categoryEntity = categoryMapper.toEntity(categoryPayload);
        log.info("created category {}", categoryEntity);
        return categoryRepository.save(categoryEntity)
                .map(categoryMapper::toPayload);
    }

    public Mono<CategoryPayload> update(CategoryPayload categoryPayload, String publicId) {
        return categoryRepository.findOne(Example.of(CategoryEntity.builder()
                        .publicId(publicId)
                        .build()))
                .switchIfEmpty(Mono.error(new EntityNotFoundException(publicId)))
                .flatMap(oldCategoryEntity -> {
                    var newEntity = categoryMapper.toEntity(categoryPayload);
                    newEntity.setPublicId(oldCategoryEntity.getPublicId());
                    categoryRepository.deleteById(oldCategoryEntity.getId()).subscribe();
                    log.info("updated category {}", newEntity);
                    return categoryRepository.save(newEntity)
                            .map(categoryMapper::toPayload);
                });
    }

    public Mono<CategoryPayload> getByPublicId(String publicId) {
        return categoryRepository.findOne(Example.of(CategoryEntity.builder()
                        .publicId(publicId)
                        .build()))
                .switchIfEmpty(Mono.error(new EntityNotFoundException(publicId)))
                .map(categoryMapper::toPayload);
    }

    public Flux<CategoryPayload> getAll() {
        return categoryRepository.findAll()
                .map(categoryMapper::toPayload);
    }

    public void delete(String publicId) {
        categoryRepository.deleteByPublicId(publicId).subscribe();
    }
}
