package com.service.category.model.repository;

import com.service.category.model.entity.CategoryEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.Set;

public interface CategoryReactiveRepository extends ReactiveMongoRepository<CategoryEntity, BigInteger> {

    Mono<Long> deleteByPublicId(String publicId);

    Flux<CategoryEntity> findByPublicIdIn(Set<String> publicIds);

    Mono<CategoryEntity> findByPublicId(String publicId);
}
