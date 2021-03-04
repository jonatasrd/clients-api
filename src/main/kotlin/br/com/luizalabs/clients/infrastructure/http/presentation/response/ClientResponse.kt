package br.com.luizalabs.clients.infrastructure.http.presentation.response

import br.com.luizalabs.clients.domain.Client
import io.swagger.annotations.ApiModel

/**
 * Client payload response.
 */
@ApiModel(value = "ClientResponse")
data class ClientResponse(
    val id: String,
    val name: String,
    val email: String,
    val wishlist: Set<String> = emptySet()
)

/**
 * Convert [Client] to [ClientResponse].
 */
internal fun Client.toResponse() = ClientResponse(
    id = this.id!!,
    name = this.name,
    email = this.email,
    wishlist = this.wishlist
)

/**
 * Convert a list of [Client] to a List of [ClientResponse].
 */
internal fun List<Client>.toResponse() = this.map { it.toResponse() }