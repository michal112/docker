package com.service.product.resource

import com.service.product.model.payload.ProductPayload
import com.service.product.service.ProductService
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/product")
class ProductResource {

    @Inject
    lateinit var productService: ProductService

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    fun create(productPayload: ProductPayload) :ProductPayload {
        return productService.create(productPayload)
    }

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

    @PUT
    @Path("/{publicId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun update(productPayload: ProductPayload, @PathParam("publicId") publicId: String) :ProductPayload {
        return productService.update(productPayload, publicId)
    }

    @DELETE
    @Path("/{publicId}")
    fun delete(@PathParam("publicId") publicId: String) {
        productService.delete(publicId)
    }

    @PUT
    @Path("/{productPublicId}/assign/brand/{brandPublicId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun assignBrand(@PathParam("productPublicId") productPublicId: String,
                    @PathParam("brandPublicId") brandPublicId: String): ProductPayload{
        return productService.assignBrand(productPublicId, brandPublicId)
    }

    @PUT
    @Path("/{productPublicId}/remove/brand")
    @Produces(MediaType.APPLICATION_JSON)
    fun removeBrand(@PathParam("productPublicId") productPublicId: String): ProductPayload {
        return productService.removeBrand(productPublicId)
    }
}