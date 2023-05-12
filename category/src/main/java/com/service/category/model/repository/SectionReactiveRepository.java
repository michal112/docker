package com.service.category.model.repository;

import com.service.category.model.entity.SectionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.math.BigInteger;

public interface SectionReactiveRepository extends ReactiveMongoRepository<SectionEntity, BigInteger> {
}
