package br.com.luizalabs.fixtures.domain

import br.com.luizalabs.clientsapi.domain.EditClientMessage
import br.com.luizalabs.fixtures.consts.ClientConsFixture

class EditClientMessageFixture {

    companion object {

        fun default() = EditClientMessage(
            id = ClientConsFixture.DEFAULT_ID,
            name = ClientConsFixture.DEFAULT_NAME,
            email = ClientConsFixture.DEFAULT_EMAIL
        )
    }
}