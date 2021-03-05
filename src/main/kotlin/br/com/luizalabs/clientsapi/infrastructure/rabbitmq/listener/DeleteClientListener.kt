package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.listener

import br.com.luizalabs.clientsapi.domain.DeleteClientMessage
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQEnvelope
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQExceptionHandler
import br.com.luizalabs.clientsapi.usecase.DeleteClientUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQConstDefinition as RabbitMQ

@Service
class DeleteClientListener(
    val deleteClientUseCase: DeleteClientUseCase
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @RabbitListener(queues = [RabbitMQ.CLIENT_DELETE_QUEUE])
    @RabbitMQExceptionHandler(
        exchange = RabbitMQ.CLIENT_DELETE_EXCHANGE,
        routing = RabbitMQ.CLIENT_DELETE_QUEUE,
        deadLetter = RabbitMQ.CLIENT_DELETE_DLQ,
        retries = 10,
        delay = 120000
    )
    fun receive(@Payload envelope: RabbitMQEnvelope<DeleteClientMessage>) {

        val deleteMessage = envelope.obj

        try {
            deleteMessage?.let {
                deleteClientUseCase.execute(deleteMessage.id)
            }
        } catch (ex: Exception) {
            logger.error("Error deleting client with id={} error={}", deleteMessage, ex.message)
            throw ex
        }
    }
}