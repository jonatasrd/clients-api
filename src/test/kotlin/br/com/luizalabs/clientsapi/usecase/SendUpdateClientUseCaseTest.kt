package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.EditClientPublisher
import br.com.luizalabs.fixtures.consts.ClientConsFixture
import br.com.luizalabs.fixtures.domain.UpdateClientFixture
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class SendUpdateClientUseCaseTest {

    @InjectMockKs
    lateinit var sendUpdateClientUseCase: SendUpdateClientUseCase

    @MockK
    lateinit var publisher: EditClientPublisher

    @Test
    fun `Should send to publisher`() {

        every { publisher.publish(any()) } answers { nothing }

        sendUpdateClientUseCase.execute(ClientConsFixture.DEFAULT_ID, UpdateClientFixture.default())

        verify(exactly = 1) { publisher.publish(any()) }

    }
}