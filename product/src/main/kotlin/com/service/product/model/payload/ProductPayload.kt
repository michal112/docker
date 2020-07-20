package com.service.product.model.payload

import java.time.LocalDate
import javax.json.bind.annotation.JsonbDateFormat

class ProductPayload(
    val publicId: String,
    val name: String,
    val price: Int,
    @JsonbDateFormat(value = "yyyy-MM-dd")
    val productionDate: LocalDate,
    @JsonbDateFormat(value = "yyyy-MM-dd")
    val bestBefore: LocalDate,
    val categoryPublicId:  String)