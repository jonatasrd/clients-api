package br.com.luizalabs.clientsapi.infrastructure.http.presentation.response

import br.com.luizalabs.clientsapi.domain.Client
import br.com.luizalabs.fixtures.consts.ClientConsFixture
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ClientCreateResponseTest {

    @Test
    fun `Should convert Client to ClientCreateResponse`() {

        val response = Client(
            ClientConsFixture.DEFAULT_ID,
            ClientConsFixture.DEFAULT_NAME,
            ClientConsFixture.DEFAULT_EMAIL,
            setOf(ClientConsFixture.DEFAULT_ID_PRODUCT)
        ).toCreateResponse()

        assertEquals(ClientConsFixture.DEFAULT_ID, response.id)
        assertEquals(ClientConsFixture.DEFAULT_NAME, response.name)
        assertEquals(ClientConsFixture.DEFAULT_EMAIL, response.email)
    }
}