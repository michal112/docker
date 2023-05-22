package com.service.category.controller;

import com.service.category.model.payload.SectionPayload;
import com.service.category.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/section")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @PostMapping
    public ResponseEntity<Mono<SectionPayload>> create(@RequestBody SectionPayload section) {
        return ResponseEntity.ok(sectionService.create(section));
    }

    @GetMapping("/{publicId}")
    public ResponseEntity<Mono<SectionPayload>> getByPublicId(@PathVariable String publicId) {
        return ResponseEntity.ok(sectionService.getByPublicId(publicId));
    }

    @GetMapping
    public ResponseEntity<Flux<SectionPayload>> getAll() {
        return ResponseEntity.ok(sectionService.getAll());
    }

    @PutMapping("/{publicId}")
    public ResponseEntity<Mono<SectionPayload>> update(@RequestBody SectionPayload section, @PathVariable String publicId) {
        return ResponseEntity.ok(sectionService.update(section, publicId));
    }

    @DeleteMapping("/{publicId}")
    public ResponseEntity<Void> delete(@PathVariable String publicId) {
        sectionService.delete(publicId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{sectionPublicId}/assign/category/{categoryPublicId}")
    public ResponseEntity<Mono<SectionPayload>> assignCategory(@PathVariable String sectionPublicId, @PathVariable String categoryPublicId) {
        return ResponseEntity.ok(sectionService.assignCategory(sectionPublicId, categoryPublicId));
    }

    @PutMapping("/{sectionPublicId}/remove/category/{categoryPublicId}")
    public ResponseEntity<Mono<SectionPayload>> removeCategory(@PathVariable String sectionPublicId, @PathVariable String categoryPublicId) {
        return ResponseEntity.ok(sectionService.removeCategory(sectionPublicId, categoryPublicId));
    }
}
