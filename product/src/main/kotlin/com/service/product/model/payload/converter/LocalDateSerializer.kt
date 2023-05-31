package com.service.product.model.payload.converter

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.time.LocalDate

class LocalDateSerializer : StdSerializer<LocalDate>(LocalDate::class.java) {

    override fun serialize(localDate: LocalDate?, jsonGenerator: JsonGenerator?, serializerProvider: SerializerProvider?) {
        val year = localDate!!.year
        val month = localDate.month.value
        val day = localDate.dayOfMonth

        jsonGenerator!!.writeObject(LocalDatePayload(year, month, day))
    }
}