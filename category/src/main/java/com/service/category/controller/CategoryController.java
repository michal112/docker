package com.service.category.controller;

import com.service.category.model.payload.CategoryPayload;
import com.service.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Mono<CategoryPayload>> create(@RequestBody CategoryPayload category) {
        return ResponseEntity.ok(categoryService.create(category));
    }

    @GetMapping("/{publicId}")
    public ResponseEntity<Mono<CategoryPayload>> getByPublicId(@PathVariable String publicId) {
        return ResponseEntity.ok(categoryService.getByPublicId(publicId));
    }

    @GetMapping
    public ResponseEntity<Flux<CategoryPayload>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PutMapping("/{publicId}")
    public ResponseEntity<Mono<CategoryPayload>> update(@RequestBody CategoryPayload category, @PathVariable String publicId) {
        return ResponseEntity.ok(categoryService.update(category, publicId));
    }

    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> delete(@PathVariable String publicId) {
        categoryService.delete(publicId);
        return ResponseEntity.noContent().build();
    }
}
