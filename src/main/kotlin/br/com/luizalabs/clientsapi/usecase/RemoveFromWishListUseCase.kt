package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.repository.ClientMongoRepository
import org.springframework.stereotype.Service

@Service
class RemoveFromWishListUseCase(
    val clientMongoRepository: ClientMongoRepository
) {

    fun execute(clientId: String, productsIds: Set<String>) {
        val result = clientMongoRepository.findById(clientId)

        if (result.isPresent) {

            val clientMongoModel = result.get()

            clientMongoModel.wishlist.removeAll(productsIds)

            clientMongoRepository.save(clientMongoModel)
        }
    }
}