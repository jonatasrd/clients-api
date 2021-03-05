package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.exception.ResourceNotFoundException
import br.com.luizalabs.clientsapi.repository.ClientMongoRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RemoveFromWishListUseCase(
    val findClientByIdUseCase: FindClientByIdUseCase,
    val clientMongoRepository: ClientMongoRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun execute(clientId: String, productsIds: Set<String>) {
        try {
            val client = findClientByIdUseCase.execute(clientId)

            client.wishlist.removeAll(productsIds)

            clientMongoRepository.save(client)

        } catch (error: ResourceNotFoundException) {
            logger.info(error.message)
        }
    }
}