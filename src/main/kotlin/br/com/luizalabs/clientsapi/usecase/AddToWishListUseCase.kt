package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.repository.ClientMongoRepository
import org.springframework.stereotype.Service

@Service
class AddToWishListUseCase(
    val getValidProductsIdsUseCase: GetValidProductsIdsUseCase,
    val clientMongoRepository: ClientMongoRepository
) {

    fun execute(clientId: String, productsIds: Set<String>) {

        val client = clientMongoRepository.findById(clientId)

        if (client.isPresent) {

            val clientMongoModel = client.get()

            val validProductsIds = getValidProductsIdsUseCase.execute(productsIds)

            if (validProductsIds.isNotEmpty()) {
                clientMongoModel.wishlist.addAll(validProductsIds)
                clientMongoRepository.save(clientMongoModel)
            }
        }
    }
}