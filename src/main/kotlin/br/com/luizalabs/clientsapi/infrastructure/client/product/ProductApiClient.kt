package br.com.luizalabs.clientsapi.infrastructure.client.product

import br.com.luizalabs.clientsapi.infrastructure.client.product.response.ProductResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(
    name = "rules",
    url = "\${endpoints.productApi.uri}"
)
interface ProductApiClient {

    @RequestMapping(
        method = [RequestMethod.GET],
        name = "product",
        value = ["/api/product/{id}/"],
        consumes = ["application/json"]
    )
    fun findById(
        @PathVariable("id") id: String
    ): ProductResponse
}