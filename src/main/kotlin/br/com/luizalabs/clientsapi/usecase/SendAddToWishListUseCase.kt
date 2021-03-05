package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.AddToWishListPublisher
import br.com.luizalabs.clientsapi.domain.WishListMessage
import org.springframework.stereotype.Service

@Service
class SendAddToWishListUseCase(
    val publisherTo: AddToWishListPublisher
) {

    fun execute(id: String, products: Set<String>) = publisherTo.publish(
        WishListMessage(id, products)
    )
}