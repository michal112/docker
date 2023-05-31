package com.service.product.resource

import com.service.product.model.payload.BrandPayload
import com.service.product.service.BrandService
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/brand")
class BrandResource {

    @Inject
    lateinit var brandService: BrandService

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    fun create(brandPayload: BrandPayload) :BrandPayload {
        return brandService.create(brandPayload)
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getBrands() :List<BrandPayload> {
        return brandService.getAll()
    }

    @GET
    @Path("/{publicId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getBrandById(@PathParam("publicId") publicId: String) :BrandPayload {
        return brandService.getByPublicId(publicId)
    }

    @PUT
    @Path("/{publicId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun update(brandPayload: BrandPayload, @PathParam("publicId") publicId: String) :BrandPayload {
        return brandService.update(brandPayload, publicId)
    }

    @DELETE
    @Path("/{publicId}")
    fun delete(@PathParam("publicId") publicId: String) {
        brandService.delete(publicId)
    }
}