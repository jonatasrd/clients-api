package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.repository.ClientMongoRepository
import br.com.luizalabs.clientsapi.repository.model.ClientMongoModel
import br.com.luizalabs.fixtures.domain.ClientDomainFixture
import br.com.luizalabs.fixtures.model.ClientMongoModelFixture
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

@ExtendWith(MockKExtension::class)
internal class FindClientsUseCaseTest {

    @InjectMockKs
    lateinit var findClientsUseCase: FindClientsUseCase

    @MockK
    lateinit var clientMongoRepository: ClientMongoRepository

    private val pagination = PageRequest.of(0, 100)

    @Test
    fun `Should return a List of clients`() {

        val pageImpl = PageImpl<ClientMongoModel>(listOf(ClientMongoModelFixture.default()))

        every { clientMongoRepository.findAll(pagination) } returns pageImpl

        val result = findClientsUseCase.execute(pagination)

        assertEquals(ClientDomainFixture.defaultSaved(), result.first())

        verify(exactly = 1) { clientMongoRepository.findAll(pagination) }

    }

    @Test
    fun `Should return an empty List of clients`() {

        val pageImpl = PageImpl<ClientMongoModel>(listOf())

        every { clientMongoRepository.findAll(pagination) } returns pageImpl

        val result = findClientsUseCase.execute(pagination)

        assertTrue(result.isEmpty())

        verify(exactly = 1) { clientMongoRepository.findAll(pagination) }

    }
}