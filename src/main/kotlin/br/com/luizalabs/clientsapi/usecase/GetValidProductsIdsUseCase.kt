package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.infrastructure.client.product.ProductApiClient
import feign.FeignException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class GetValidProductsIdsUseCase(
    val productApi: ProductApiClient
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun execute(productsIds: Set<String>): Set<String> {

        val validProductsIds = mutableSetOf<String>()

        productsIds.forEach {
            try {

                val product = productApi.findById(it)
                validProductsIds.add(product.id)

            } catch (ex: FeignException) {
                if (ex.status() == 404)
                    logger.info("Product not find by id: $it.")
                else throw ex
            }
        }
        return validProductsIds
    }
}