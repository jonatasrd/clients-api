package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.listener

import br.com.luizalabs.clientsapi.domain.WishListMessage
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQEnvelope
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQExceptionHandler
import br.com.luizalabs.clientsapi.usecase.AddToWishListUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQConstDefinition as RabbitMQ

@Service
class AddToWishListListener(
    val addToWishListUseCase: AddToWishListUseCase
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @RabbitListener(queues = [RabbitMQ.CLIENT_ADD_WISHLIST_QUEUE])
    @RabbitMQExceptionHandler(
        exchange = RabbitMQ.CLIENT_ADD_WISHLIST_EXCHANGE,
        routing = RabbitMQ.CLIENT_ADD_WISHLIST_QUEUE,
        deadLetter = RabbitMQ.CLIENT_ADD_WISHLIST_DLQ,
        retries = 10,
        delay = 120000
    )
    fun receive(@Payload envelope: RabbitMQEnvelope<WishListMessage>) {

        val message = envelope.obj

        try {
            message?.let {
                addToWishListUseCase.execute(message.clientId, message.productsIds)
            }
        } catch (ex: Exception) {
            logger.error("Error adding products to wishlist message={} error={}", envelope, ex.message)
            throw ex
        }
    }
}