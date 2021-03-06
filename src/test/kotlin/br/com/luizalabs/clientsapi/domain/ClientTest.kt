package br.com.luizalabs.clientsapi.domain

import br.com.luizalabs.fixtures.consts.ClientConsFixture
import br.com.luizalabs.fixtures.model.ClientMongoModelFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ClientTest {

    @Test
    fun `Should convert Client model to ClientMongoModel`() {

        val clientMongoModel = Client(
            null,
            ClientConsFixture.DEFAULT_NAME,
            ClientConsFixture.DEFAULT_EMAIL,
            setOf("teste")

        ).toNewMongoModel()

        assertNull(clientMongoModel._id)
        assertEquals(ClientConsFixture.DEFAULT_NAME, clientMongoModel.name)
        assertEquals(ClientConsFixture.DEFAULT_EMAIL, clientMongoModel.email)
        assertTrue(clientMongoModel.wishlist.isEmpty())

    }

    @Test
    fun `Should convert ClientMongoModel to Client model`() {

        val clientModel = ClientMongoModelFixture.full().toDomain()

        assertEquals(ClientConsFixture.DEFAULT_ID, clientModel.id)
        assertEquals(ClientConsFixture.DEFAULT_NAME, clientModel.name)
        assertEquals(ClientConsFixture.DEFAULT_EMAIL, clientModel.email)
        assertEquals(ClientConsFixture.DEFAULT_ID_PRODUCT, clientModel.wishlist.first())

    }

}