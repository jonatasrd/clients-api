package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.listener

import br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQEnvelope
import br.com.luizalabs.clientsapi.usecase.DeleteClientUseCase
import br.com.luizalabs.fixtures.domain.DeleteClientMessageFixture
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class DeleteClientListenerTest {

    @InjectMockKs
    lateinit var deleteClientListener: DeleteClientListener

    @MockK
    lateinit var deleteClientUseCase: DeleteClientUseCase

    @Test
    fun `Should be able to listen delete client messages`() {

        val deleteClientMessage = DeleteClientMessageFixture.default()

        every { deleteClientUseCase.execute(any()) } answers { nothing }

        val envelope = RabbitMQEnvelope(deleteClientMessage)

        deleteClientListener.receive(envelope)

        verify(exactly = 1) { deleteClientUseCase.execute(any()) }

    }
}