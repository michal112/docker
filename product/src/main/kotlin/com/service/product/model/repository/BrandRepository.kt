package com.service.product.model.repository

import com.service.product.model.entity.BrandEntity
import io.quarkus.mongodb.panache.PanacheMongoRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class BrandRepository : PanacheMongoRepository<BrandEntity> {

    fun create(brandEntity: BrandEntity): BrandEntity {
        persist(brandEntity)
        return brandEntity
    }

    fun getByPublicId(publicId :String): BrandEntity {
        return find("publicId", publicId).firstResult()
    }

    fun getAll(): List<BrandEntity> {
        return listAll()
    }

    fun updateBrand(brandEntity: BrandEntity) : BrandEntity {
        update(brandEntity)
        return brandEntity
    }

    fun deleteBrand(brandEntity: BrandEntity) {
        delete(brandEntity)
    }
}