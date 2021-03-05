package br.com.luizalabs.fixtures.domain

import br.com.luizalabs.clientsapi.domain.UpdateClient
import br.com.luizalabs.fixtures.consts.ClientConsFixture

class UpdateClientFixture {

    companion object {

        fun default() = UpdateClient(
            name = ClientConsFixture.DEFAULT_NAME,
            email = ClientConsFixture.DEFAULT_EMAIL
        )
    }
}