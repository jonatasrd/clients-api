package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config

import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate

/**
 * RabbitMQPublisher domain.
 */
data class RabbitMQPublisher(
    val exchange: Exchange,
    val template: RabbitTemplate,
    val routingKey: String = ""
) {
    /**
     * Overloaded constructor to receive a Queue.
     */
    constructor(
        exchange: Exchange,
        template: RabbitTemplate,
        queue: Queue
    ) : this(exchange, template, queue.name)

    fun publish(message: RabbitMQMessage) {
        template.convertAndSend(exchange.name, routingKey, message.envelop) { processor: Message ->
            val headers = processor.messageProperties.headers
            message.headers.let { headers.putAll(it) }
            processor
        }
    }
}
