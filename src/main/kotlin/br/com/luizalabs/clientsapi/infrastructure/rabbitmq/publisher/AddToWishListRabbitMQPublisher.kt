package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.publisher

import br.com.luizalabs.clientsapi.domain.AddToWishListPublisher
import br.com.luizalabs.clientsapi.domain.WishListMessage
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQEnvelope
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQMessage
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQPublisher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

/**
 * RabbitMQ add to wishlist publisher.
 */
@Service
class AddToWishListRabbitMQPublisher(
    @Qualifier("publisher:add_wishlist") val publisher: RabbitMQPublisher
) : AddToWishListPublisher {

    override fun publish(wishListMessage: WishListMessage) {
        val envelope: RabbitMQEnvelope<WishListMessage> = RabbitMQEnvelope.of(wishListMessage)
        val message: RabbitMQMessage = RabbitMQMessage.withEnvelop(envelope).build()
        publisher.publish(message)
    }

}