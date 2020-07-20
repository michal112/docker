package com.service.product.resource

import com.service.product.model.payload.ProductPayload
import com.service.product.service.ProductService
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/product")
class ProductResource {

    @Inject
    lateinit var productService: ProductService

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getProducts() :List<ProductPayload> {
        return productService.getAll()
    }

    @GET
    @Path("/{publicId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getProductById(@PathParam("publicId") publicId: String) :ProductPayload {
        return productService.getByPublicId(publicId)
    }
}