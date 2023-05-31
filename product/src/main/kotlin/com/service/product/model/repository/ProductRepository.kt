package com.service.product.model.repository

import com.service.product.model.entity.ProductEntity
import io.quarkus.mongodb.panache.PanacheMongoRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ProductRepository : PanacheMongoRepository<ProductEntity> {

    fun create(productEntity: ProductEntity): ProductEntity {
        persist(productEntity)
        return productEntity
    }

    fun getByPublicId(publicId :String): ProductEntity {
        return find("publicId", publicId).firstResult()
    }

    fun getByBrandPublicId(brandPublicId :String): List<ProductEntity> {
        return find("brandPublicId", brandPublicId).list()
    }

    fun getAll(): List<ProductEntity> {
        return listAll()
    }

    fun updateProduct(productEntity: ProductEntity) :ProductEntity {
        update(productEntity)
        return productEntity
    }

    fun deleteProduct(productEntity: ProductEntity) {
        delete(productEntity)
    }
}