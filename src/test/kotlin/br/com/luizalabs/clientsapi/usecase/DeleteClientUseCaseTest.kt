package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.repository.ClientMongoRepository
import br.com.luizalabs.fixtures.consts.ClientConsFixture
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class DeleteClientUseCaseTest {

    @InjectMockKs
    lateinit var deleteClientUseCase: DeleteClientUseCase

    @MockK
    lateinit var clientMongoRepository: ClientMongoRepository

    @Test
    fun `Should delete a client`() {

        every { clientMongoRepository.deleteById(any()) } answers { nothing }

        deleteClientUseCase.execute(ClientConsFixture.DEFAULT_ID)

        verify(exactly = 1) { clientMongoRepository.deleteById(any()) }

    }

}