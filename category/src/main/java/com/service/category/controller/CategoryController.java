package com.service.category.controller;

import com.service.category.model.payload.CategoryPayload;
import com.service.category.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{publicId}")
    public Mono<CategoryPayload> getByPublicId(@PathVariable String publicId) {
        return categoryService.getByPublicId(publicId);
    }

    @GetMapping
    public Flux<CategoryPayload> getAll() {
        return categoryService.getAll();
    }
}
