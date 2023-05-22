package com.service.category.model.repository;

import com.service.category.model.entity.SectionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface SectionReactiveRepository extends ReactiveMongoRepository<SectionEntity, BigInteger> {

    Mono<SectionEntity> findByPublicId(String publicId);

    Mono<Long> deleteByPublicId(String publicId);

    Flux<SectionEntity> findByCategoriesContaining(String categoryPublicId);
}
