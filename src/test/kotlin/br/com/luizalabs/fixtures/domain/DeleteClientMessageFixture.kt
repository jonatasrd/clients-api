package br.com.luizalabs.fixtures.domain

import br.com.luizalabs.clientsapi.domain.DeleteClientMessage
import br.com.luizalabs.fixtures.consts.ClientConsFixture

class DeleteClientMessageFixture {

    companion object {

        fun default() = DeleteClientMessage(
            id = ClientConsFixture.DEFAULT_ID
        )
    }
}