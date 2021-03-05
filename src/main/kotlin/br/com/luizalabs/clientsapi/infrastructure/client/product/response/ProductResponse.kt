package br.com.luizalabs.clientsapi.infrastructure.client.product.response

data class ProductResponse(
    val id: String,
    val title: String,
    val price: Double,
    val brand: String,
    val image: String,
    val reviewScore: Double?
)
