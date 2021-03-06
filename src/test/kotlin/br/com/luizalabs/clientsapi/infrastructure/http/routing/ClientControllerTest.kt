package br.com.luizalabs.clientsapi.infrastructure.http.routing

import br.com.luizalabs.clientsapi.usecase.FindClientByIdUseCase
import br.com.luizalabs.clientsapi.usecase.FindClientsUseCase
import br.com.luizalabs.clientsapi.usecase.RegisterClientUseCase
import br.com.luizalabs.clientsapi.usecase.SendAddToWishListUseCase
import br.com.luizalabs.clientsapi.usecase.SendDeleteClientUseCase
import br.com.luizalabs.clientsapi.usecase.SendRemoveFromWishListUseCase
import br.com.luizalabs.clientsapi.usecase.SendUpdateClientUseCase
import br.com.luizalabs.commons.RegexConsts
import br.com.luizalabs.fixtures.consts.ClientConsFixture
import br.com.luizalabs.fixtures.domain.ClientDomainFixture
import br.com.luizalabs.fixtures.payload.request.ClientRequestFixture
import br.com.luizalabs.fixtures.payload.response.ClientResponseFixture
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.skyscreamer.jsonassert.Customization
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher
import org.skyscreamer.jsonassert.comparator.CustomComparator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

/**
 * Unit test for [ClientController]
 */
@ExtendWith(SpringExtension::class)
@WebMvcTest(ClientController::class, excludeAutoConfiguration = [SecurityAutoConfiguration::class])
@ActiveProfiles("test")
internal class ClientControllerTest(@Autowired val mvc: MockMvc) {

    @MockkBean
    lateinit var registerClientUseCase: RegisterClientUseCase

    @MockkBean
    lateinit var findClientsUseCase: FindClientsUseCase

    @MockkBean
    lateinit var findClientByIdUseCase: FindClientByIdUseCase

    @MockkBean
    lateinit var sendUpdateClientUseCase: SendUpdateClientUseCase

    @MockkBean
    lateinit var sendDeleteClientUseCase: SendDeleteClientUseCase

    @MockkBean
    lateinit var sendAddToWishListUseCase: SendAddToWishListUseCase

    @MockkBean
    lateinit var sendRemoveFromWishListUseCase: SendRemoveFromWishListUseCase

    private val baseUri: String = "/api/clients"
    private val baseUriWithId: String = "/api/clients/{id}"
    private val pagination = PageRequest.of(0, 100)

    @Nested
    inner class SaveClient {

        @Test
        fun `Should save new client (status 201)`() {

            every { registerClientUseCase.execute(any()) } returns ClientDomainFixture.defaultSaved()

            val result =
                mvc.perform(
                    MockMvcRequestBuilders.post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ClientRequestFixture.DEFAULT)
                ).andExpect(MockMvcResultMatchers.status().isCreated)
                    .andReturn()
            val content = result.response.contentAsString

            JSONAssert.assertEquals(ClientResponseFixture.DEFAULT, content, JSONCompareMode.LENIENT)
        }

        @Test
        fun `Should throw error 400 when request no contains name`() {

            val result =
                mvc.perform(
                    MockMvcRequestBuilders.post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ClientRequestFixture.REQUEST_WITHOUT_NAME)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest)
                    .andReturn()
            val content = result.response.contentAsString

            JSONAssert.assertEquals(
                ClientResponseFixture.ERROR_WITHOUT_NAME,
                content,
                getComparatorForTimestamp()
            )
        }

        @Test
        fun `Should throw error 400 when request no contains email`() {

            val result =
                mvc.perform(
                    MockMvcRequestBuilders.post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ClientRequestFixture.REQUEST_WITHOUT_EMAIL)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest)
                    .andReturn()
            val content = result.response.contentAsString

            JSONAssert.assertEquals(
                ClientResponseFixture.ERROR_WITHOUT_EMAIL,
                content,
                getComparatorForTimestamp()
            )
        }

        @Test
        fun `Should throw error 400 when request contains an invalid email`() {

            val result =
                mvc.perform(
                    MockMvcRequestBuilders.post(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ClientRequestFixture.REQUEST_INVALID_EMAIL)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest)
                    .andReturn()
            val content = result.response.contentAsString

            JSONAssert.assertEquals(
                ClientResponseFixture.ERROR_INVALID_EMAIL,
                content,
                getComparatorForTimestamp()
            )
        }

    }

    @Nested
    inner class FindClient {

        @Test
        fun `Should find clients (status 200)`() {
            every { findClientsUseCase.execute(pagination) } returns listOf(
                ClientDomainFixture.defaultSaved()
            )

            val result =
                mvc.perform(
                    MockMvcRequestBuilders.get(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk)
                    .andReturn()
            val content = result.response.contentAsString

            JSONAssert.assertEquals(
                ClientResponseFixture.DEFAULT_LIST,
                content,
                JSONCompareMode.LENIENT
            )
        }

        @Test
        fun `Should return an empty list (status 200)`() {
            every { findClientsUseCase.execute(pagination) } returns emptyList()

            val result =
                mvc.perform(
                    MockMvcRequestBuilders.get(baseUri)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk)
                    .andReturn()
            val content = result.response.contentAsString

            JSONAssert.assertEquals(
                ClientResponseFixture.EMPTY_LIST,
                content,
                JSONCompareMode.LENIENT
            )
        }

        @Test
        fun `Should find client by id (status 200)`() {

            every { findClientByIdUseCase.execute(any()) } returns ClientDomainFixture.defaultSaved()

            val result =
                mvc.perform(
                    MockMvcRequestBuilders.get(baseUriWithId, ClientConsFixture.DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk)
                    .andReturn()
            val content = result.response.contentAsString

            JSONAssert.assertEquals(
                ClientResponseFixture.DEFAULT,
                content,
                JSONCompareMode.LENIENT
            )
        }
    }

    @Nested
    inner class EditClient {

        @Test
        fun `Should edit a client (status 202)`() {

            every { sendUpdateClientUseCase.execute(any(), any()) } answers { nothing }

            mvc.perform(
                MockMvcRequestBuilders.put(baseUriWithId, ClientConsFixture.DEFAULT_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ClientRequestFixture.DEFAULT)
            ).andExpect(MockMvcResultMatchers.status().isAccepted)
                .andReturn()
        }

        @Test
        fun `Should throw error 400 when request contains an invalid email`() {

            val result =
                mvc.perform(
                    MockMvcRequestBuilders.put(baseUriWithId, ClientConsFixture.DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ClientRequestFixture.REQUEST_INVALID_EMAIL)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest)
                    .andReturn()
            val content = result.response.contentAsString

            JSONAssert.assertEquals(
                ClientResponseFixture.ERROR_INVALID_EMAIL,
                content,
                getComparatorForTimestamp()
            )
        }

    }

    @Nested
    inner class DeleteClient {

        @Test
        fun `Should delete a client (status 202)`() {

            every { sendDeleteClientUseCase.execute(any()) } answers { nothing }

            mvc.perform(
                MockMvcRequestBuilders.delete(baseUriWithId, ClientConsFixture.DEFAULT_ID)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(MockMvcResultMatchers.status().isAccepted)
                .andReturn()
        }
    }

    @Nested
    inner class WishListClient {

        @Test
        fun `Should add to wishlist (status 202)`() {

            every { sendAddToWishListUseCase.execute(any(), any()) } answers { nothing }

            mvc.perform(
                MockMvcRequestBuilders.put("/api/clients/{id}/wishlist/add", ClientConsFixture.DEFAULT_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ClientRequestFixture.WISHLIST)
            ).andExpect(MockMvcResultMatchers.status().isAccepted)
                .andReturn()
        }

        @Test
        fun `Should remove from wishlist (status 202)`() {

            every { sendRemoveFromWishListUseCase.execute(any(), any()) } answers { nothing }

            mvc.perform(
                MockMvcRequestBuilders.put("/api/clients/{id}/wishlist/remove", ClientConsFixture.DEFAULT_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ClientRequestFixture.WISHLIST)
            ).andExpect(MockMvcResultMatchers.status().isAccepted)
                .andReturn()
        }
    }

    private fun getComparatorForTimestamp() = CustomComparator(
        JSONCompareMode.NON_EXTENSIBLE,
        getCustomizationForTimestamp()
    )

    private fun getCustomizationForTimestamp() = Customization(
        "timestamp",
        RegularExpressionValueMatcher<Any>(RegexConsts.TIMESTAMP)
    )
}