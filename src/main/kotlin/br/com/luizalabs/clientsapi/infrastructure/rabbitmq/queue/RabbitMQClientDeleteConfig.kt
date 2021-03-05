package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.queue

import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQPublisher
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.ExchangeBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQConstDefinition as QueueDefinition

/**
 * RabbitMQ client delete configs messages.
 */
@Configuration
class RabbitMQClientDeleteConfig {

    @Bean("exchange:delete_client-exchange")
    fun cancelScheduleExchange(): Exchange = ExchangeBuilder
        .topicExchange(QueueDefinition.CLIENT_DELETE_EXCHANGE)
        .delayed()
        .build()

    @Bean("queue:delete_client-dead-letter")
    fun cancelScheduleQueueDLQ(): Queue = QueueBuilder
        .durable(QueueDefinition.CLIENT_DELETE_DLQ)
        .build()

    @Bean("queue:delete_client")
    fun cancelScheduleQueue(): Queue = QueueBuilder
        .durable(QueueDefinition.CLIENT_DELETE_QUEUE)
        .withArgument("x-dead-letter-exchange", cancelScheduleExchange().name)
        .withArgument("x-dead-letter-routing-key", cancelScheduleQueueDLQ().name)
        .build()

    @Bean("binding:delete_client")
    fun cancelScheduleBinding(): Binding = BindingBuilder
        .bind(cancelScheduleQueue()).to(cancelScheduleExchange())
        .with(cancelScheduleQueue().name)
        .noargs()

    @Bean("binding:delete_client-dead-letter")
    fun cancelScheduleDLQBinging(): Binding? = BindingBuilder
        .bind(cancelScheduleQueueDLQ()).to(cancelScheduleExchange())
        .with(cancelScheduleQueueDLQ().name)
        .noargs()

    @Bean("publisher:delete_client")
    @Autowired
    fun cancelSchedulePublisher(template: RabbitTemplate): RabbitMQPublisher {
        return RabbitMQPublisher(cancelScheduleExchange(), template, "delete_client")
    }

    @Bean("binding:delete_client_retry")
    fun cancelScheduleRetryBinding(): Binding? {
        val queue: Queue = cancelScheduleQueue()
        return BindingBuilder
            .bind(queue)
            .to(cancelScheduleExchange())
            .with("delete_client")
            .noargs()
    }

}