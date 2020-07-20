package com.service.category.service;

import com.service.category.model.mapper.CategoryMapper;
import com.service.category.model.payload.CategoryPayload;
import com.service.category.model.repository.CategoryReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;

    private final CategoryReactiveRepository categoryRepository;

    public CategoryService(CategoryMapper categoryMapper, CategoryReactiveRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    public Mono<CategoryPayload> getByPublicId(String publicId) {
        return categoryRepository.findByPublicId(publicId)
                .map(categoryMapper::toPayload);
    }

    public Flux<CategoryPayload> getAll() {
        return categoryRepository.findAll()
                .map(categoryMapper::toPayload);
    }
}
