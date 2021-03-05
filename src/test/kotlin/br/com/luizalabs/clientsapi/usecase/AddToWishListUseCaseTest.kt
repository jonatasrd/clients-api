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
internal class AddToWishListUseCaseTest {

    @InjectMockKs
    lateinit var addToWishListUseCase: AddToWishListUseCase

    @MockK
    lateinit var clientMongoRepository: ClientMongoRepository

    @MockK
    lateinit var getValidProductsIdsUseCase: GetValidProductsIdsUseCase

    @Test
    fun `Should save on wishlist`() {

        val model = ClientMongoModelFixture.default()

        val products = setOf(ClientConsFixture.DEFAULT_ID_PRODUCT)

        every { clientMongoRepository.findById(any()) } answers { Optional.of(model) }
        every { getValidProductsIdsUseCase.execute(any()) } answers { products }
        every { clientMongoRepository.save(model) } answers { model }

        addToWishListUseCase.execute(ClientConsFixture.DEFAULT_ID, products)

        verify(exactly = 1) { clientMongoRepository.findById(any()) }
        verify(exactly = 1) { clientMongoRepository.save(model) }
        verify(exactly = 1) { getValidProductsIdsUseCase.execute(any()) }

    }

    @Test
    fun `Should not save if client not found`() {

        val model = ClientMongoModelFixture.default()

        val products = setOf(ClientConsFixture.DEFAULT_ID_PRODUCT)

        every { clientMongoRepository.findById(any()) } answers { Optional.empty() }

        addToWishListUseCase.execute(ClientConsFixture.DEFAULT_ID, products)

        verify(exactly = 1) { clientMongoRepository.findById(any()) }
        verify(exactly = 0) { clientMongoRepository.save(model) }
        verify(exactly = 0) { getValidProductsIdsUseCase.execute(any()) }

    }

}