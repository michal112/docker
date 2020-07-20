package com.service.product.model

import com.service.product.model.entity.ProductEntity
import com.service.product.model.payload.ProductPayload

fun ProductEntity.toPayload(): ProductPayload {
    return ProductPayload(publicId, name, price, productionDate, bestBefore, categoryPublicId)
}