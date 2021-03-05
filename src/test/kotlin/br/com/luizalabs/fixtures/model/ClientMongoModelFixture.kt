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

        fun toSave() = ClientMongoModel(
            _id = null,
            name = ClientConsFixture.DEFAULT_NAME,
            email = ClientConsFixture.DEFAULT_EMAIL
        )
    }
}