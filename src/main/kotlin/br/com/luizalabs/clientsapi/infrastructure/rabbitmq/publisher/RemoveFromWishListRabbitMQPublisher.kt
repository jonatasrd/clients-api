package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.publisher

import br.com.luizalabs.clientsapi.domain.RemoveFromWishListPublisher
import br.com.luizalabs.clientsapi.domain.WishListMessage
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQEnvelope
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQMessage
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQPublisher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

/**
 * RabbitMQ remove from wishlist publisher.
 */
@Service
class RemoveFromWishListRabbitMQPublisher(
    @Qualifier("publisher:remove_wishlist") val publisher: RabbitMQPublisher
) : RemoveFromWishListPublisher {

    override fun publish(wishListMessage: WishListMessage) {
        val envelope: RabbitMQEnvelope<WishListMessage> = RabbitMQEnvelope.of(wishListMessage)
        val message: RabbitMQMessage = RabbitMQMessage.withEnvelop(envelope).build()
        publisher.publish(message)
    }

}