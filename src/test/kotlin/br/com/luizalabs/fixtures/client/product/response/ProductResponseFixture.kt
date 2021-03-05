package br.com.luizalabs.fixtures.client.product.response

import br.com.luizalabs.clientsapi.infrastructure.client.product.response.ProductResponse
import br.com.luizalabs.fixtures.consts.ClientConsFixture

class ProductResponseFixture {

    companion object {

        fun default() = ProductResponse(
            id = ClientConsFixture.DEFAULT_ID_PRODUCT,
            title = "Churrasqueira Elétrica Mondial 1800W",
            price = 159.0,
            brand = "mondial",
            image = "http://challenge-api.luizalabs.com/images/571fa8cc-2ee7-5ab4-b388-06d55fd8ab2f.jpg",
            reviewScore = 4.352941
        )
    }
}