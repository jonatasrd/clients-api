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
 * RabbitMQ client add to wishlist configs messages.
 */
@Configuration
class RabbitMQAddToWishListConfig {

    @Bean("exchange:add_wishlist-exchange")
    fun cancelScheduleExchange(): Exchange = ExchangeBuilder
        .topicExchange(QueueDefinition.CLIENT_ADD_WISHLIST_EXCHANGE)
        .delayed()
        .build()

    @Bean("queue:add_wishlist-dead-letter")
    fun cancelScheduleQueueDLQ(): Queue = QueueBuilder
        .durable(QueueDefinition.CLIENT_ADD_WISHLIST_DLQ)
        .build()

    @Bean("queue:add_wishlist")
    fun cancelScheduleQueue(): Queue = QueueBuilder
        .durable(QueueDefinition.CLIENT_ADD_WISHLIST_QUEUE)
        .withArgument("x-dead-letter-exchange", cancelScheduleExchange().name)
        .withArgument("x-dead-letter-routing-key", cancelScheduleQueueDLQ().name)
        .build()

    @Bean("binding:add_wishlist")
    fun cancelScheduleBinding(): Binding = BindingBuilder
        .bind(cancelScheduleQueue()).to(cancelScheduleExchange())
        .with(cancelScheduleQueue().name)
        .noargs()

    @Bean("binding:add_wishlist-dead-letter")
    fun cancelScheduleDLQBinging(): Binding? = BindingBuilder
        .bind(cancelScheduleQueueDLQ()).to(cancelScheduleExchange())
        .with(cancelScheduleQueueDLQ().name)
        .noargs()

    @Bean("publisher:add_wishlist")
    @Autowired
    fun cancelSchedulePublisher(template: RabbitTemplate): RabbitMQPublisher {
        return RabbitMQPublisher(cancelScheduleExchange(), template, "add_wishlist")
    }

    @Bean("binding:add_wishlist_retry")
    fun cancelScheduleRetryBinding(): Binding? {
        val queue: Queue = cancelScheduleQueue()
        return BindingBuilder
            .bind(queue)
            .to(cancelScheduleExchange())
            .with("add_wishlist")
            .noargs()
    }

}