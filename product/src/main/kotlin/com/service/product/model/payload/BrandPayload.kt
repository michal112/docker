package com.service.product.model.payload

data class BrandPayload(
    val publicId: String,
    val name: String
) {
    constructor() :this("", "")
}