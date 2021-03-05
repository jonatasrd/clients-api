package br.com.luizalabs.clientsapi.domain

interface AddToWishListPublisher {

    fun publish(wishListMessage: WishListMessage)
}