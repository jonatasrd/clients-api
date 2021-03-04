package br.com.luizalabs.clientsapi.domain

data class Client(
    val id: String?,
    val name: String,
    val email: String,
    val wishlist: Set<String> = emptySet()
) {
    /**
     * Overloaded client domain constructor, without id.
     *
     * @constructor Creates client domain without id.
     */
    constructor(
        name: String,
        email: String,
        wishlist: Set<String>
    ) : this(
        id = null,
        name = name,
        email = email,
        wishlist = wishlist
    )
}
