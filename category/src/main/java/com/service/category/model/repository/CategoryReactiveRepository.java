package com.service.category.model.repository;

import com.service.category.model.entity.CategoryEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CategoryReactiveRepository extends ReactiveMongoRepository<CategoryEntity, Long> {

    @Query("{ 'publicId' : ?0 }")
    Mono<CategoryEntity> findByPublicId(String publicId);
}
