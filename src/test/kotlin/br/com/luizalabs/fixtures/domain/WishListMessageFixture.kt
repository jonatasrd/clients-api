package br.com.luizalabs.fixtures.domain

import br.com.luizalabs.clientsapi.domain.WishListMessage
import br.com.luizalabs.fixtures.consts.ClientConsFixture

class WishListMessageFixture {

    companion object {

        fun default() = WishListMessage(
            clientId = ClientConsFixture.DEFAULT_ID,
            productsIds = setOf(ClientConsFixture.DEFAULT_ID_PRODUCT)
        )
    }
}