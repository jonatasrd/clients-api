package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.exception.ResourceNotFoundException
import br.com.luizalabs.clientsapi.repository.ClientMongoRepository
import br.com.luizalabs.fixtures.consts.ClientConsFixture
import br.com.luizalabs.fixtures.domain.ClientDomainFixture
import br.com.luizalabs.fixtures.model.ClientMongoModelFixture
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
internal class FindClientByIdUseCaseTest {

    @InjectMockKs
    lateinit var findClientByIdUseCase: FindClientByIdUseCase

    @MockK
    lateinit var clientMongoRepository: ClientMongoRepository

    @Test
    fun `Should find a client by id`() {

        every { clientMongoRepository.findById(any()) } answers { Optional.of(ClientMongoModelFixture.default()) }

        val result = findClientByIdUseCase.execute(ClientConsFixture.DEFAULT_ID)

        assertEquals(ClientDomainFixture.defaultSaved(), result)

        verify(exactly = 1) { clientMongoRepository.findById(any()) }

    }

    @Test
    fun `Should throw resourceNotFoundException when not find client by id`() {

        every { clientMongoRepository.findById(any()) } returns Optional.empty()

        val error = assertThrows(ResourceNotFoundException::class.java) {
            findClientByIdUseCase.execute(ClientConsFixture.DEFAULT_ID)
        }

        assertEquals(error.message, "Client not found by id: ${ClientConsFixture.DEFAULT_ID}!")

        verify(exactly = 1) { clientMongoRepository.findById(any()) }
    }

}