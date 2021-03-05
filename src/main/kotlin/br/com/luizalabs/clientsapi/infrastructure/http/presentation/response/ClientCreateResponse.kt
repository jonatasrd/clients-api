package br.com.luizalabs.clientsapi.infrastructure.http.presentation.response

import br.com.luizalabs.clientsapi.domain.Client
import io.swagger.annotations.ApiModel

/**
 * Client payload response.
 */
@ApiModel(value = "ClientCreateResponse")
data class ClientCreateResponse(
    val id: String,
    val name: String,
    val email: String
)

/**
 * Convert [Client] to [ClientCreateResponse].
 */
internal fun Client.toCreateResponse() = ClientCreateResponse(
    id = this.id!!,
    name = this.name,
    email = this.email
)