package br.com.luizalabs.clientsapi.infrastructure.http.presentation.response

import br.com.luizalabs.clientsapi.domain.Client
import br.com.luizalabs.fixtures.consts.ClientConsFixture
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ClientResponseTest {

    private val client = Client(
        ClientConsFixture.DEFAULT_ID,
        ClientConsFixture.DEFAULT_NAME,
        ClientConsFixture.DEFAULT_EMAIL,
        setOf(ClientConsFixture.DEFAULT_ID_PRODUCT)
    )

    private val clients = listOf(client)

    @Test
    fun `Should convert Client to ClientResponse`() {

        val response = client.toResponse()

        assertEquals(ClientConsFixture.DEFAULT_ID, response.id)
        assertEquals(ClientConsFixture.DEFAULT_NAME, response.name)
        assertEquals(ClientConsFixture.DEFAULT_EMAIL, response.email)
        assertEquals(ClientConsFixture.DEFAULT_ID_PRODUCT, response.wishlist.first())
    }

    @Test
    fun `Should convert a list of Client to a list of ClientResponse`() {

        val response = clients.toResponse()

        assertEquals(ClientConsFixture.DEFAULT_ID, response.first().id)
        assertEquals(ClientConsFixture.DEFAULT_NAME, response.first().name)
        assertEquals(ClientConsFixture.DEFAULT_EMAIL, response.first().email)
        assertEquals(ClientConsFixture.DEFAULT_ID_PRODUCT, response.first().wishlist.first())
    }
}