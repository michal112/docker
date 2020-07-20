package com.service.product

import com.service.product.model.entity.ProductEntity
import com.service.product.model.repository.ProductRepository
import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.StartupEvent
import java.time.LocalDate
import java.time.Month
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.inject.Inject

@ApplicationScoped
class ProductsApplication {

    @Inject
    lateinit var productRepository: ProductRepository

    val data = listOf(
            ProductEntity("648fca2c-9aeb-4773-8d15-536927f44cc0",
                "Makrela wędzona",
                1450,
                LocalDate.of(2019, Month.APRIL, 12),
                LocalDate.of(2019, Month.APRIL, 29),
                "d557db5a-a911-4a5c-92d5-feb1293f5fba"),
            ProductEntity("22f77e87-abff-4946-a9e7-457ade8af197",
                "Polędwica z dorsza",
                6599,
                LocalDate.of(2019, Month.APRIL, 2),
                LocalDate.of(2019, Month.APRIL, 15),
                "d557db5a-a911-4a5c-92d5-feb1293f5fba"),
            ProductEntity("20da8e84-d189-4e63-9875-fe9855f04c39",
                    "Łosoś wędzony",
                    4399,
                    LocalDate.of(2019, Month.APRIL, 22),
                    LocalDate.of(2019, Month.MAY, 15),
                    "d557db5a-a911-4a5c-92d5-feb1293f5fba"),
            ProductEntity("9e1eadd1-824d-4722-af12-397dab2a9c81",
                    "Kropla Beskidu",
                    199,
                    LocalDate.of(2019, Month.APRIL, 12),
                    LocalDate.of(2021, Month.APRIL, 29),
                    "83dda947-a4ee-4676-b929-13b52f9e0426"),
            ProductEntity("8067993a-b21f-4a40-b47f-b365743f95b9",
                    "Nałęczowianka",
                    219,
                    LocalDate.of(2019, Month.APRIL, 1),
                    LocalDate.of(2021, Month.APRIL, 15),
                    "83dda947-a4ee-4676-b929-13b52f9e0426"),
            ProductEntity("a98c5843-b9e2-448f-9c10-2c8626dcea1e",
                    "Muszynianka",
                    210,
                    LocalDate.of(2019, Month.APRIL, 3),
                    LocalDate.of(2021, Month.MAY, 15),
                    "83dda947-a4ee-4676-b929-13b52f9e0426")
    )

    fun onStart(@Observes event: StartupEvent) {
        productRepository.persist(data)
    }

    fun onStop(@Observes event: ShutdownEvent) {
        data.forEach { productRepository.delete(it) }
    }
}