package br.com.luizalabs.clientsapi.domain

import br.com.luizalabs.clientsapi.repository.client.ClientMongoModel

data class Client(
    val id: String?,
    val name: String,
    val email: String,
    val wishlist: Set<String> = emptySet()
) {
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

/**
 * Convert [Client] domain to [ClientMongoModel]
 */
internal fun Client.toNewMongoModel() = ClientMongoModel(
    name = this.name,
    email = this.email
)

/**
 * Convert [ClientMongoModel] domain to [Client]
 */
internal fun ClientMongoModel.toDomain() = Client(
    id = this._id,
    name = this.name,
    email = this.email,
    wishlist = this.wishlist
)
