package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.publisher

import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQEnvelope
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQMessage
import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQPublisher
import br.com.luizalabs.fixtures.domain.WishListMessageFixture
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class RemoveFromWishListRabbitMQPublisherTest {

    @InjectMockKs
    lateinit var removeFromWishListRabbitMQPublisher: RemoveFromWishListRabbitMQPublisher

    @MockK
    lateinit var publisher: RabbitMQPublisher

    @Test
    fun `Should be able to publish remove products from wishlist message`() {

        every { publisher.publish(any()) } answers { nothing }

        val message = WishListMessageFixture.default()

        removeFromWishListRabbitMQPublisher.publish(message)

        val envelope = RabbitMQEnvelope(message)
        val rabbitMQMessage: RabbitMQMessage = RabbitMQMessage.withEnvelop(envelope).build()

        verify { publisher.publish(rabbitMQMessage) }

    }
}