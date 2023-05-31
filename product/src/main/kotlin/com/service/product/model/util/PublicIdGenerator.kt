package com.service.product.model.util

import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class PublicIdGenerator {

    fun uuid() :String {
        return UUID.randomUUID().toString();
    }
}