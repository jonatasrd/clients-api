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
 * RabbitMQ client remove from wishlist configs messages.
 */
@Configuration
class RabbitMQRemoveFromWishListConfig {

    @Bean("exchange:remove_wishlist-exchange")
    fun cancelScheduleExchange(): Exchange = ExchangeBuilder
        .topicExchange(QueueDefinition.CLIENT_REMOVE_WISHLIST_EXCHANGE)
        .delayed()
        .build()

    @Bean("queue:remove_wishlist-dead-letter")
    fun cancelScheduleQueueDLQ(): Queue = QueueBuilder
        .durable(QueueDefinition.CLIENT_REMOVE_WISHLIST_DLQ)
        .build()

    @Bean("queue:remove_wishlist")
    fun cancelScheduleQueue(): Queue = QueueBuilder
        .durable(QueueDefinition.CLIENT_REMOVE_WISHLIST_QUEUE)
        .withArgument("x-dead-letter-exchange", cancelScheduleExchange().name)
        .withArgument("x-dead-letter-routing-key", cancelScheduleQueueDLQ().name)
        .build()

    @Bean("binding:remove_wishlist")
    fun cancelScheduleBinding(): Binding = BindingBuilder
        .bind(cancelScheduleQueue()).to(cancelScheduleExchange())
        .with(cancelScheduleQueue().name)
        .noargs()

    @Bean("binding:remove_wishlist-dead-letter")
    fun cancelScheduleDLQBinging(): Binding? = BindingBuilder
        .bind(cancelScheduleQueueDLQ()).to(cancelScheduleExchange())
        .with(cancelScheduleQueueDLQ().name)
        .noargs()

    @Bean("publisher:remove_wishlist")
    @Autowired
    fun cancelSchedulePublisher(template: RabbitTemplate): RabbitMQPublisher {
        return RabbitMQPublisher(cancelScheduleExchange(), template, "remove_wishlist")
    }

    @Bean("binding:remove_wishlist_retry")
    fun cancelScheduleRetryBinding(): Binding? {
        val queue: Queue = cancelScheduleQueue()
        return BindingBuilder
            .bind(queue)
            .to(cancelScheduleExchange())
            .with("remove_wishlist")
            .noargs()
    }

}