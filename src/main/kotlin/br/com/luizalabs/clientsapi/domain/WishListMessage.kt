package br.com.luizalabs.clientsapi.domain

data class WishListMessage(
    val clientId: String,
    val productsIds: Set<String>
)
