package com.service.product.model

import com.service.product.model.entity.BrandEntity
import com.service.product.model.payload.BrandPayload
import com.service.product.model.util.PublicIdGenerator

fun BrandEntity.toPayload(): BrandPayload {
    return BrandPayload(publicId, name)
}

fun BrandPayload.toEntity(publicIdGenerator: PublicIdGenerator): BrandEntity {
    return BrandEntity(publicIdGenerator.uuid(), name)
}

