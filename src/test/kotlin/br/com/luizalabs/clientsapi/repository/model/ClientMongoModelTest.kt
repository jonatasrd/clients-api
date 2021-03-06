package br.com.luizalabs.clientsapi.repository.model

import br.com.luizalabs.clientsapi.domain.EditClientMessage
import br.com.luizalabs.fixtures.consts.ClientConsFixture
import br.com.luizalabs.fixtures.model.ClientMongoModelFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ClientMongoModelTest {

    @Test
    fun `Should update ClientMongoModel with edit message`() {

        val clientMongoModel = ClientMongoModelFixture.full()

        assertEquals(ClientConsFixture.DEFAULT_NAME, clientMongoModel.name)
        assertEquals(ClientConsFixture.DEFAULT_EMAIL, clientMongoModel.email)
        assertEquals(ClientConsFixture.DEFAULT_ID_PRODUCT, clientMongoModel.wishlist.first())


        val editMessage = EditClientMessage(
            id = ClientConsFixture.DEFAULT_ID,
            name = ClientConsFixture.OTHER_NAME,
            email = ClientConsFixture.OTHER_EMAIL
        )

        val updatedMongoMongoModel = clientMongoModel.toUpdate(editMessage)

        assertEquals(ClientConsFixture.OTHER_NAME, updatedMongoMongoModel.name)
        assertEquals(ClientConsFixture.OTHER_EMAIL, updatedMongoMongoModel.email)
        assertEquals(ClientConsFixture.DEFAULT_ID_PRODUCT, updatedMongoMongoModel.wishlist.first())

    }
}