package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.repository.ClientMongoRepository
import br.com.luizalabs.fixtures.consts.ClientConsFixture
import br.com.luizalabs.fixtures.model.ClientMongoModelFixture
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
internal class RemoveFromWishListUseCaseTest {

    @InjectMockKs
    lateinit var removeFromWishListUseCase: RemoveFromWishListUseCase

    @MockK
    lateinit var clientMongoRepository: ClientMongoRepository

    @Test
    fun `Should remove from wishlist`() {

        val model = ClientMongoModelFixture.default()

        val products = setOf(ClientConsFixture.DEFAULT_ID_PRODUCT)

        every { clientMongoRepository.findById(any()) } answers { Optional.of(model) }
        every { clientMongoRepository.save(model) } answers { model }

        removeFromWishListUseCase.execute(ClientConsFixture.DEFAULT_ID, products)

        verify(exactly = 1) { clientMongoRepository.findById(any()) }
        verify(exactly = 1) { clientMongoRepository.save(model) }

    }

    @Test
    fun `Should not remove if client not found`() {

        val model = ClientMongoModelFixture.default()

        val products = setOf(ClientConsFixture.DEFAULT_ID_PRODUCT)

        every { clientMongoRepository.findById(any()) } answers { Optional.empty() }

        removeFromWishListUseCase.execute(ClientConsFixture.DEFAULT_ID, products)

        verify(exactly = 1) { clientMongoRepository.findById(any()) }
        verify(exactly = 0) { clientMongoRepository.save(model) }

    }

}