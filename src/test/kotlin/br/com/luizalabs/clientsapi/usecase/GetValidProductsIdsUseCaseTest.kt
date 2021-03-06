package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.infrastructure.client.product.ProductApiClient
import br.com.luizalabs.fixtures.client.product.response.ProductResponseFixture
import br.com.luizalabs.fixtures.consts.ClientConsFixture
import feign.FeignException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class GetValidProductsIdsUseCaseTest {

    @InjectMockKs
    lateinit var getValidProductsIdsUseCase: GetValidProductsIdsUseCase

    @MockK
    lateinit var productApi: ProductApiClient

    @Test
    fun `Should return valid ids`() {

        every { productApi.findById(any()) } answers { ProductResponseFixture.default() }

        val result = getValidProductsIdsUseCase.execute(setOf(ClientConsFixture.DEFAULT_ID_PRODUCT))

        assertEquals(ClientConsFixture.DEFAULT_ID_PRODUCT, result.first())

        verify(exactly = 1) { productApi.findById(any()) }

    }

    @Test
    fun `Should call ProductApiClient for every product id`() {

        every { productApi.findById(any()) } answers { ProductResponseFixture.default() }

        getValidProductsIdsUseCase.execute(
            setOf(
                ClientConsFixture.DEFAULT_ID_PRODUCT,
                ClientConsFixture.OTHER_ID_PRODUCT,
                ClientConsFixture.ANY_ID_PRODUCT
            )
        )

        verify(exactly = 3) { productApi.findById(any()) }

    }

    @Test
    fun `Should not throw exception if api returns 404`() {

        every { productApi.findById(any()) } throws TestFeignClientException(404, "test", byteArrayOf(1))

        val result = getValidProductsIdsUseCase.execute(setOf(ClientConsFixture.DEFAULT_ID_PRODUCT))

        assertTrue(result.isEmpty())

        verify(exactly = 1) { productApi.findById(any()) }

    }

    @Test
    fun `Should throw Exception if api returns status code different from 404`() {

        every { productApi.findById(any()) } throws TestFeignClientException(500, "test", byteArrayOf(1))


        assertThrows(TestFeignClientException::class.java) {
            getValidProductsIdsUseCase.execute(setOf(ClientConsFixture.DEFAULT_ID_PRODUCT))
        }
    }

}

class TestFeignClientException(
    status: Int,
    message: String?,
    content: ByteArray?
) : FeignException(status, message, content)