package com.service.product.model.repository

import com.service.product.model.entity.ProductEntity
import io.quarkus.mongodb.panache.PanacheMongoRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ProductRepository : PanacheMongoRepository<ProductEntity> {

    fun getByPublicId(publicId :String): ProductEntity {
        return find("publicId", publicId).firstResult()
    }

    fun getAll(): List<ProductEntity> {
        return listAll()
    }
}