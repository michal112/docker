package com.service.product.service

import com.service.product.model.payload.BrandPayload
import com.service.product.model.repository.BrandRepository
import com.service.product.model.repository.ProductRepository
import com.service.product.model.toEntity
import com.service.product.model.toPayload
import com.service.product.model.util.PublicIdGenerator
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class BrandService {

    @Inject
    lateinit var publicIdGenerator: PublicIdGenerator

    @Inject
    lateinit var brandRepository: BrandRepository

    @Inject
    lateinit var productRepository: ProductRepository

    fun create(brandPayload: BrandPayload): BrandPayload {
        return brandRepository.create(brandPayload.toEntity(publicIdGenerator)).toPayload()
    }

    fun getAll(): List<BrandPayload> {
        return brandRepository.getAll().map { it.toPayload() }
    }

    fun getByPublicId(publicId: String): BrandPayload {
        return brandRepository.getByPublicId(publicId).toPayload()
    }

    fun update(brandPayload: BrandPayload, brandPublicId: String): BrandPayload {
        val oldEntity = brandRepository.getByPublicId(brandPublicId)
        val newEntity = brandPayload.toEntity(publicIdGenerator)
        newEntity.publicId = oldEntity.publicId
        newEntity.id = oldEntity.id
        return brandRepository.updateBrand(newEntity).toPayload()
    }

    fun delete(publicId: String) {
        brandRepository.deleteBrand(brandRepository.getByPublicId(publicId))
        productRepository.getByBrandPublicId(publicId).map {
            it.brandPublicId = null
            productRepository.updateProduct(it)
        }
    }
}