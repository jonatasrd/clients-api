package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.RemoveFromWishListPublisher
import br.com.luizalabs.clientsapi.domain.WishListMessage
import org.springframework.stereotype.Service

@Service
class SendRemoveFromWishListUseCase(
    val publisher: RemoveFromWishListPublisher
) {

    fun execute(products: Set<String>) = publisher.publish(WishListMessage(products))
}