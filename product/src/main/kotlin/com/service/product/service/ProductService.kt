package com.service.product.service

import com.service.product.model.payload.ProductPayload
import com.service.product.model.repository.BrandRepository
import com.service.product.model.repository.ProductRepository
import com.service.product.model.toEntity
import com.service.product.model.toPayload
import com.service.product.model.util.PublicIdGenerator
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class ProductService {

    @Inject
    lateinit var publicIdGenerator: PublicIdGenerator

    @Inject
    lateinit var productRepository: ProductRepository

    @Inject
    lateinit var brandRepository: BrandRepository

    fun create(productPayload: ProductPayload): ProductPayload {
        return productRepository.create(productPayload.toEntity(publicIdGenerator)).toPayload()
    }

    fun getAll(): List<ProductPayload> {
        return productRepository.getAll().map {
            when (Objects.nonNull(it.brandPublicId)) {
                 true -> it.toPayload(brandRepository.getByPublicId(it.brandPublicId!!))
                 false -> it.toPayload()
            }
        }
    }

    fun getByPublicId(publicId: String): ProductPayload {
        val entity = productRepository.getByPublicId(publicId)
        return when (Objects.nonNull(entity.brandPublicId)) {
            true -> entity.toPayload(brandRepository.getByPublicId(entity.brandPublicId!!))
            false -> entity.toPayload()
        }
    }

    fun update(productPayload: ProductPayload, productPublicId: String): ProductPayload {
        val oldEntity = productRepository.getByPublicId(productPublicId)
        val newEntity = productPayload.toEntity(publicIdGenerator)
        newEntity.publicId = oldEntity.publicId
        newEntity.categoryPublicId = oldEntity.categoryPublicId
        newEntity.brandPublicId = oldEntity.brandPublicId
        newEntity.id = oldEntity.id
        productRepository.updateProduct(newEntity)

        return when (Objects.nonNull(newEntity.brandPublicId)) {
            true -> newEntity.toPayload(brandRepository.getByPublicId(newEntity.brandPublicId!!))
            false -> newEntity.toPayload()
        }
    }

    fun delete(publicId: String) {
        productRepository.deleteProduct(productRepository.getByPublicId(publicId))
    }

    fun assignBrand(productPublicId: String, brandPublicId: String): ProductPayload {
        val product = productRepository.getByPublicId(productPublicId)
        val brand = brandRepository.getByPublicId(brandPublicId)

        product.brandPublicId = brand.publicId
        return productRepository.updateProduct(product).toPayload(brand)
    }

    fun removeBrand(productPublicId: String): ProductPayload {
        val product = productRepository.getByPublicId(productPublicId)

        product.brandPublicId = null
        return productRepository.updateProduct(product).toPayload()
    }
}