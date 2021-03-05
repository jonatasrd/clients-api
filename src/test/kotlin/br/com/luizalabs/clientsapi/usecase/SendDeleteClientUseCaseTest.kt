package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.DeleteClientPublisher
import br.com.luizalabs.fixtures.consts.ClientConsFixture
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class SendDeleteClientUseCaseTest {

    @InjectMockKs
    lateinit var sendDeleteClientUseCase: SendDeleteClientUseCase

    @MockK
    lateinit var publisher: DeleteClientPublisher

    @Test
    fun `Should send to publisher`() {

        every { publisher.publish(any()) } answers { nothing }

        sendDeleteClientUseCase.execute(ClientConsFixture.DEFAULT_ID)

        verify(exactly = 1) { publisher.publish(any()) }

    }
}