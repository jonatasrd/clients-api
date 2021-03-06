package br.com.luizalabs.clientsapi.domain

import br.com.luizalabs.fixtures.consts.ClientConsFixture
import br.com.luizalabs.fixtures.domain.UpdateClientFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UpdateClientTest {

    @Test
    fun `Should convert UpdateClient to EditClientMessage`() {

        val editClientMessage = UpdateClientFixture.default().toMessage(
            ClientConsFixture.DEFAULT_ID
        )

        assertEquals(ClientConsFixture.DEFAULT_ID, editClientMessage.id)
        assertEquals(ClientConsFixture.DEFAULT_NAME, editClientMessage.name)
        assertEquals(ClientConsFixture.DEFAULT_EMAIL, editClientMessage.email)
    }

}