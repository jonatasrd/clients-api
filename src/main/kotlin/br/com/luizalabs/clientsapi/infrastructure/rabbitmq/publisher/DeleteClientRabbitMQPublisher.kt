package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.publisher

import br.com.luizalabs.clientsapi.domain.DeleteClientMessage
import br.com.luizalabs.clientsapi.domain.DeleteClientPublisher
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQEnvelope
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQMessage
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQPublisher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

/**
 * RabbitMQ edit client publisher.
 */
@Service
class DeleteClientRabbitMQPublisher(
    @Qualifier("publisher:delete_client") val publisher: RabbitMQPublisher
) : DeleteClientPublisher {

    override fun publish(deleteMessage: DeleteClientMessage) {
        val envelope: RabbitMQEnvelope<DeleteClientMessage> = RabbitMQEnvelope.of(deleteMessage)
        val message: RabbitMQMessage = RabbitMQMessage.withEnvelop(envelope).build()
        publisher.publish(message)
    }

}