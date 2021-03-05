package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.listener

import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQEnvelope
import br.com.luizalabs.clientsapi.usecase.AddToWishListUseCase
import br.com.luizalabs.fixtures.domain.WishListMessageFixture
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class AddToWishListListenerTest {

    @InjectMockKs
    lateinit var addToWishListListener: AddToWishListListener

    @MockK
    lateinit var addToWishListUseCase: AddToWishListUseCase

    @Test
    fun `Should be able to listen add wishlist messages`() {

        val wishListMessage = WishListMessageFixture.default()

        every { addToWishListUseCase.execute(any(), any()) } answers { nothing }

        val envelope = RabbitMQEnvelope(wishListMessage)

        addToWishListListener.receive(envelope)

        verify(exactly = 1) { addToWishListUseCase.execute(any(), any()) }

    }
}