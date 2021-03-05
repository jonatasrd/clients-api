package br.com.luizalabs.clientsapi.domain

interface RemoveFromWishListPublisher {

    fun publish(wishListMessage: WishListMessage)
}