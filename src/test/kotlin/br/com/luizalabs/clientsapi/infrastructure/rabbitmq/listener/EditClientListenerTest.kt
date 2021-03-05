package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.listener

import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQEnvelope
import br.com.luizalabs.clientsapi.usecase.UpdateClientUseCase
import br.com.luizalabs.fixtures.domain.EditClientMessageFixture
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class EditClientListenerTest {

    @InjectMockKs
    lateinit var editClientListener: EditClientListener

    @MockK
    lateinit var updateClientUseCase: UpdateClientUseCase

    @Test
    fun `Should be able to listen add wishlist messages`() {

        val editClientMessage = EditClientMessageFixture.default()

        every { updateClientUseCase.execute(any()) } answers { nothing }

        val envelope = RabbitMQEnvelope(editClientMessage)

        editClientListener.receive(envelope)

        verify(exactly = 1) { updateClientUseCase.execute(any()) }

    }
}