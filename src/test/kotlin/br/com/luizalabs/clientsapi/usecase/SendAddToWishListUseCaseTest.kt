package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.AddToWishListPublisher
import br.com.luizalabs.fixtures.consts.ClientConsFixture
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class SendAddToWishListUseCaseTest {

    @InjectMockKs
    lateinit var sendAddToWishListUseCase: SendAddToWishListUseCase

    @MockK
    lateinit var publisher: AddToWishListPublisher

    @Test
    fun `Should send to publisher`() {

        every { publisher.publish(any()) } answers { nothing }

        sendAddToWishListUseCase.execute(ClientConsFixture.DEFAULT_ID, setOf(ClientConsFixture.DEFAULT_ID_PRODUCT))

        verify(exactly = 1) { publisher.publish(any()) }

    }
}