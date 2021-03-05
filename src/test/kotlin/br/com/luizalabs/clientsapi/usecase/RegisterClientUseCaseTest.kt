package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.exception.ResourceAlreadyExistsException
import br.com.luizalabs.clientsapi.repository.ClientMongoRepository
import br.com.luizalabs.fixtures.domain.ClientDomainFixture
import br.com.luizalabs.fixtures.model.ClientMongoModelFixture
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.dao.DuplicateKeyException

@ExtendWith(MockKExtension::class)
internal class RegisterClientUseCaseTest {

    @InjectMockKs
    lateinit var registerClientUseCase: RegisterClientUseCase

    @MockK
    lateinit var clientMongoRepository: ClientMongoRepository

    @Test
    fun `Should register a client`() {

        every { clientMongoRepository.save(ClientMongoModelFixture.toSave()) } answers { ClientMongoModelFixture.default() }

        val result = registerClientUseCase.execute(ClientDomainFixture.toSave())

        assertEquals(ClientDomainFixture.defaultSaved(), result)

        verify(exactly = 1) { clientMongoRepository.save(ClientMongoModelFixture.toSave()) }

    }

    @Test
    fun `Should throw ResourceAlreadyExistsException when email already exists`() {

        every { clientMongoRepository.save(ClientMongoModelFixture.toSave()) } throws mockk<DuplicateKeyException>()

        Assertions.assertThrows(ResourceAlreadyExistsException::class.java) {
            registerClientUseCase.execute(ClientDomainFixture.toSave())
        }

        verify(exactly = 1) { clientMongoRepository.save(ClientMongoModelFixture.toSave()) }
    }

}