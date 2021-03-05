package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.publisher

import br.com.luizalabs.clientsapi.domain.EditClientMessage
import br.com.luizalabs.clientsapi.domain.EditClientPublisher
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQEnvelope
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQMessage
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQPublisher

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

/**
 * RabbitMQ edit client publisher.
 */
@Service
class EditClientRabbitMQPublisher(
    @Qualifier("publisher:edit_client") val publisher: RabbitMQPublisher
) : EditClientPublisher {

    override fun publish(editMessage: EditClientMessage) {
        val envelope: RabbitMQEnvelope<EditClientMessage> = RabbitMQEnvelope.of(editMessage)
        val message: RabbitMQMessage = RabbitMQMessage.withEnvelop(envelope).build()
        publisher.publish(message)
    }

}