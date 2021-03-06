package br.com.luizalabs.fixtures.model

import br.com.luizalabs.clientsapi.repository.model.ClientMongoModel
import br.com.luizalabs.fixtures.consts.ClientConsFixture

class ClientMongoModelFixture {

    companion object {

        fun default() = ClientMongoModel(
            _id = ClientConsFixture.DEFAULT_ID,
            name = ClientConsFixture.DEFAULT_NAME,
            email = ClientConsFixture.DEFAULT_EMAIL
        )

        fun full() = ClientMongoModel(
            _id = ClientConsFixture.DEFAULT_ID,
            name = ClientConsFixture.DEFAULT_NAME,
            email = ClientConsFixture.DEFAULT_EMAIL,
            wishlist = mutableSetOf(ClientConsFixture.DEFAULT_ID_PRODUCT)
        )

        fun withSameId() = ClientMongoModel(
            _id = ClientConsFixture.DEFAULT_ID,
            name = ClientConsFixture.OTHER_NAME,
            email = ClientConsFixture.DEFAULT_EMAIL,
            wishlist = mutableSetOf(ClientConsFixture.DEFAULT_ID_PRODUCT)
        )

        fun withSameEmail() = ClientMongoModel(
            _id = ClientConsFixture.OTHER_ID,
            name = ClientConsFixture.OTHER_NAME,
            email = ClientConsFixture.DEFAULT_EMAIL,
            wishlist = mutableSetOf(ClientConsFixture.DEFAULT_ID_PRODUCT)
        )

        fun toSave() = ClientMongoModel(
            _id = null,
            name = ClientConsFixture.DEFAULT_NAME,
            email = ClientConsFixture.OTHER_EMAIL
        )

        fun saved() = ClientMongoModel(
            _id = ClientConsFixture.OTHER_ID,
            name = ClientConsFixture.DEFAULT_NAME,
            email = ClientConsFixture.OTHER_EMAIL
        )
    }
}