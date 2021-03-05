package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.listener

import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQEnvelope
import br.com.luizalabs.clientsapi.usecase.RemoveFromWishListUseCase
import br.com.luizalabs.fixtures.domain.WishListMessageFixture
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class RemoveFromWishListListenerTest {

    @InjectMockKs
    lateinit var removeFromWishListListener: RemoveFromWishListListener

    @MockK
    lateinit var removeFromWishListUseCase: RemoveFromWishListUseCase

    @Test
    fun `Should be able to listen remove wishlist messages`() {

        val wishListMessage = WishListMessageFixture.default()

        every { removeFromWishListUseCase.execute(any(), any()) } answers { nothing }

        val envelope = RabbitMQEnvelope(wishListMessage)

        removeFromWishListListener.receive(envelope)

        verify(exactly = 1) { removeFromWishListUseCase.execute(any(), any()) }

    }
}