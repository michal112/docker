package com.service.product.model.payload

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.service.product.model.payload.converter.LocalDateDeserializer
import com.service.product.model.payload.converter.LocalDateSerializer
import java.time.LocalDate

class ProductPayload {

    lateinit var publicId: String
    lateinit var name: String
    var price: Int = 0
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    lateinit var productionDate: LocalDate
    var categoryPublicId: String? = null
    var sectionPublicId: String? = null
    var brand: BrandPayload? = null
}