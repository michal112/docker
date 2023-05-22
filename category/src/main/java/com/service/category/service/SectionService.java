package com.service.category.service;

import com.service.category.exception.AssignEntityNotFoundException;
import com.service.category.exception.EntityNotFoundException;
import com.service.category.model.mapper.SectionMapper;
import com.service.category.model.payload.SectionPayload;
import com.service.category.model.repository.CategoryReactiveRepository;
import com.service.category.model.repository.SectionReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class SectionService {

    private final SectionMapper sectionMapper;

    private final SectionReactiveRepository sectionReactiveRepository;

    private final CategoryReactiveRepository categoryReactiveRepository;

    public Mono<SectionPayload> create(SectionPayload sectionPayload) {
        var sectionEntity = sectionMapper.toEntity(sectionPayload);
        log.info("created section {}", sectionEntity);
        return sectionReactiveRepository.save(sectionEntity)
                .map(it -> sectionMapper.toPayload(it, Collections.emptyList()));
    }

    public Mono<SectionPayload> update(SectionPayload sectionPayload, String publicId) {
        return sectionReactiveRepository.findByPublicId(publicId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(publicId)))
                .flatMap(oldSectionEntity -> {
                    var newEntity = sectionMapper.toEntity(sectionPayload);
                    newEntity.setPublicId(oldSectionEntity.getPublicId());
                    newEntity.setCategories(oldSectionEntity.getCategories());
                    sectionReactiveRepository.deleteById(oldSectionEntity.getId()).subscribe();
                    log.info("updated section {}", newEntity);
                    return sectionReactiveRepository.save(newEntity)
                            .flatMap(sectionEntity -> categoryReactiveRepository.findByPublicIdIn(sectionEntity.getCategories())
                                    .collectList()
                                    .map(categories -> sectionMapper.toPayload(sectionEntity, categories))
                            );
                });
    }

    public Mono<SectionPayload> getByPublicId(String publicId) {
        return sectionReactiveRepository.findByPublicId(publicId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(publicId)))
                .flatMap(sectionEntity -> categoryReactiveRepository.findByPublicIdIn(sectionEntity.getCategories())
                            .collectList()
                            .map(categories -> sectionMapper.toPayload(sectionEntity, categories))
                );
    }

    public Flux<SectionPayload> getAll() {
        return sectionReactiveRepository.findAll()
                .flatMap(section -> Flux.from(categoryReactiveRepository.findByPublicIdIn(section.getCategories())
                        .collectList()
                        .map(categories -> sectionMapper.toPayload(section, categories)))
                );
    }

    public void delete(String publicId) {
        sectionReactiveRepository.deleteByPublicId(publicId).subscribe();
    }

    public Mono<SectionPayload> assignCategory(String sectionPublicId, String categoryPublicId) {
        return sectionReactiveRepository.findByPublicId(sectionPublicId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(sectionPublicId)))
                .flatMap(sectionEntity -> categoryReactiveRepository.findByPublicId(categoryPublicId)
                        .switchIfEmpty(Mono.error(new EntityNotFoundException(categoryPublicId)))
                        .flatMap(categoryEntity -> {
                            sectionEntity.getCategories().add(categoryEntity.getPublicId());
                            return sectionReactiveRepository.save(sectionEntity)
                                    .flatMap(section -> categoryReactiveRepository.findByPublicIdIn(section.getCategories())
                                            .collectList()
                                            .map(it -> sectionMapper.toPayload(section, it)));
                        })
                );
    }

    public Mono<SectionPayload> removeCategory(String sectionPublicId, String categoryPublicId) {
        return sectionReactiveRepository.findByPublicId(sectionPublicId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(sectionPublicId)))
                .flatMap(sectionEntity -> categoryReactiveRepository.findByPublicId(categoryPublicId)
                        .switchIfEmpty(Mono.error(new EntityNotFoundException(categoryPublicId)))
                        .flatMap(categoryEntity -> {
                            var deleted = sectionEntity.getCategories().remove(categoryEntity.getPublicId());
                            if (deleted) {
                                return sectionReactiveRepository.save(sectionEntity)
                                        .flatMap(section -> categoryReactiveRepository.findByPublicIdIn(section.getCategories())
                                                .collectList()
                                                .map(it -> sectionMapper.toPayload(section, it))
                                        );
                            } else {
                                return Mono.error(new AssignEntityNotFoundException(categoryPublicId, sectionPublicId));
                            }
                        })
                );
    }
}
