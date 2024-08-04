package com.service.product.model

import com.service.product.model.entity.BrandEntity
import com.service.product.model.entity.ProductEntity
import com.service.product.model.payload.ProductPayload
import com.service.product.model.util.PublicIdGenerator

fun ProductEntity.toPayload(): ProductPayload {
    val payload = ProductPayload()
    payload.publicId = publicId
    payload.productionDate = productionDate
    payload.name = name
    payload.price = price
    payload.categoryPublicId = categoryPublicId
    payload.sectionPublicId = sectionPublicId
    return payload
}

fun ProductEntity.toPayload(brandEntity: BrandEntity): ProductPayload {
    val payload = toPayload()
    payload.brand = brandEntity.toPayload()
    return payload
}

fun ProductPayload.toEntity(publicIdGenerator: PublicIdGenerator): ProductEntity {
    return ProductEntity(publicIdGenerator.uuid(), name, price, productionDate, categoryPublicId, null, sectionPublicId)
}

