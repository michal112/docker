package com.service.category.service;

import com.service.category.exception.EntityNotFoundException;
import com.service.category.model.entity.SectionEntity;
import com.service.category.model.mapper.CategoryMapper;
import com.service.category.model.payload.CategoryPayload;
import com.service.category.model.repository.CategoryReactiveRepository;
import com.service.category.model.repository.SectionReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryMapper categoryMapper;

    private final CategoryReactiveRepository categoryReactiveRepository;

    private final SectionReactiveRepository sectionReactiveRepository;

    public Mono<CategoryPayload> create(CategoryPayload categoryPayload) {
        var categoryEntity = categoryMapper.toEntity(categoryPayload);
        log.info("created category {}", categoryEntity);
        return categoryReactiveRepository.save(categoryEntity)
                .map(categoryMapper::toPayload);
    }

    public Mono<CategoryPayload> update(CategoryPayload categoryPayload, String publicId) {
        return categoryReactiveRepository.findByPublicId(publicId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(publicId)))
                .flatMap(oldCategoryEntity -> {
                    var newEntity = categoryMapper.toEntity(categoryPayload);
                    newEntity.setPublicId(oldCategoryEntity.getPublicId());
                    categoryReactiveRepository.deleteById(oldCategoryEntity.getId()).subscribe();
                    log.info("updated category {}", newEntity);
                    return categoryReactiveRepository.save(newEntity)
                            .map(categoryMapper::toPayload);
                });
    }

    public Mono<CategoryPayload> getByPublicId(String publicId) {
        return categoryReactiveRepository.findByPublicId(publicId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(publicId)))
                .map(categoryMapper::toPayload);
    }

    public Flux<CategoryPayload> getAll() {
        return categoryReactiveRepository.findAll()
                .map(categoryMapper::toPayload);
    }

    public void delete(String publicId) {
        categoryReactiveRepository.deleteByPublicId(publicId).subscribe();
        sectionReactiveRepository.findByCategoriesContaining(publicId)
                .flatMap(section -> {
                    section.getCategories().remove(publicId);
                    return sectionReactiveRepository.save(section);
                })
                .subscribe();
    }
}
