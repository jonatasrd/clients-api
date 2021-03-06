package br.com.luizalabs.clientsapi.infrastructure.http.presentation.request

import br.com.luizalabs.fixtures.consts.ClientConsFixture
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class CreateClientRequestTest {

    @Test
    fun `Should convert CreateClientRequest to Client`() {

        val client = CreateClientRequest(
            ClientConsFixture.DEFAULT_NAME,
            ClientConsFixture.DEFAULT_EMAIL
        ).toDomain()

        Assertions.assertEquals(ClientConsFixture.DEFAULT_NAME, client.name)
        Assertions.assertEquals(ClientConsFixture.DEFAULT_EMAIL, client.email)
        Assertions.assertTrue(client.wishlist.isEmpty())

    }
}