package com.service.product.model.entity

import io.quarkus.mongodb.panache.MongoEntity
import io.quarkus.mongodb.panache.PanacheMongoEntity

@MongoEntity(collection = "brand")
data class BrandEntity(
    var publicId: String,
    var name: String
) : PanacheMongoEntity()