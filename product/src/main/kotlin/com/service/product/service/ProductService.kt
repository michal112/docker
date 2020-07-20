package com.service.product.service

import com.service.product.model.payload.ProductPayload
import com.service.product.model.repository.ProductRepository
import com.service.product.model.toPayload
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class ProductService {

    @Inject
    lateinit var productRepository: ProductRepository

    fun getAll(): List<ProductPayload> {
        return productRepository.getAll().map { it.toPayload() }
    }

    fun getByPublicId(publicId: String): ProductPayload {
        return productRepository.getByPublicId(publicId).toPayload()
    }
}