package com.service.product.model.entity

import io.quarkus.mongodb.panache.MongoEntity
import io.quarkus.mongodb.panache.PanacheMongoEntity
import java.time.LocalDate

@MongoEntity(collection = "product")
data class ProductEntity(
    var publicId: String,
    var name: String,
    var price: Int,
    var productionDate: LocalDate,
    var categoryPublicId: String?,
    var brandPublicId: String?,
    var sectionPublicId: String?
) : PanacheMongoEntity()