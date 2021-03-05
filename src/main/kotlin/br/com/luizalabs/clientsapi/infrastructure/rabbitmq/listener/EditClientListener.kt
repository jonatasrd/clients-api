package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.listener

import br.com.luizalabs.clientsapi.domain.EditClientMessage
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQEnvelope
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQExceptionHandler
import br.com.luizalabs.clientsapi.usecase.UpdateClientUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQConstDefinition as RabbitMQ

@Service
class EditClientListener(
    val updateClientUseCase: UpdateClientUseCase
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @RabbitListener(queues = [RabbitMQ.CLIENT_EDIT_QUEUE])
    @RabbitMQExceptionHandler(
        exchange = RabbitMQ.CLIENT_EDIT_EXCHANGE,
        routing = RabbitMQ.CLIENT_EDIT_QUEUE,
        deadLetter = RabbitMQ.CLIENT_EDIT_DLQ,
        retries = 10,
        delay = 120000
    )
    fun receive(@Payload envelope: RabbitMQEnvelope<EditClientMessage>) {

        val editMessage = envelope.obj

        try {
            editMessage?.let {
                updateClientUseCase.execute(editMessage)
            }
        } catch (ex: Exception) {
            logger.error("Error editing client with message={} error={}", envelope, ex.message)
            throw ex
        }
    }
}