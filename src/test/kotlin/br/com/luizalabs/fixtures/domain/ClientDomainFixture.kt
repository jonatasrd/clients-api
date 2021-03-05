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
    }
}