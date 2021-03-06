package br.com.luizalabs.fixtures.domain

import br.com.luizalabs.clientsapi.domain.Client
import br.com.luizalabs.fixtures.consts.ClientConsFixture

open class ClientDomainFixture {

    companion object {

        fun defaultSaved() = Client(
            id = ClientConsFixture.DEFAULT_ID,
            name = ClientConsFixture.DEFAULT_NAME,
            email = ClientConsFixture.DEFAULT_EMAIL
        )

        fun toSave() = Client(
            id = null,
            name = ClientConsFixture.DEFAULT_NAME,
            email = ClientConsFixture.OTHER_EMAIL
        )

        fun saved() = Client(
            id = ClientConsFixture.OTHER_ID,
            name = ClientConsFixture.DEFAULT_NAME,
            email = ClientConsFixture.OTHER_EMAIL
        )
    }
}