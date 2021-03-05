package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.RemoveFromWishListPublisher
import br.com.luizalabs.fixtures.consts.ClientConsFixture
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class SendRemoveFromWishListUseCaseTest {

    @InjectMockKs
    lateinit var sendRemoveFromWishListUseCase: SendRemoveFromWishListUseCase

    @MockK
    lateinit var publisher: RemoveFromWishListPublisher

    @Test
    fun `Should send to publisher`() {

        every { publisher.publish(any()) } answers { nothing }

        sendRemoveFromWishListUseCase.execute(ClientConsFixture.DEFAULT_ID, setOf(ClientConsFixture.DEFAULT_ID_PRODUCT))

        verify(exactly = 1) { publisher.publish(any()) }

    }
}