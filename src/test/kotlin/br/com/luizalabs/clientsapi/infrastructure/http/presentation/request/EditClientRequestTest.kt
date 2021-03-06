package br.com.luizalabs.clientsapi.infrastructure.http.presentation.request

import br.com.luizalabs.fixtures.consts.ClientConsFixture
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class EditClientRequestTest {

    @Test
    fun `Should convert EditClientRequest to UpdateClient`() {

        val updateClient = EditClientRequest(
            ClientConsFixture.DEFAULT_NAME,
            ClientConsFixture.DEFAULT_EMAIL
        ).toDomain()

        assertEquals(ClientConsFixture.DEFAULT_NAME, updateClient.name)
        assertEquals(ClientConsFixture.DEFAULT_EMAIL, updateClient.email)

    }
}