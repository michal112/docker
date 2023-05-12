package com.service.category.model.repository;

import com.service.category.model.entity.CategoryEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public interface CategoryReactiveRepository extends ReactiveMongoRepository<CategoryEntity, BigInteger> {

    Mono<Long> deleteByPublicId(String publicId);
}
