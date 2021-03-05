package br.com.luizalabs.clientsapi.domain

data class UpdateClient(
    val name: String?,
    val email: String?
)

/**
 * Convert [UpdateClient] domain to [Client]
 */
internal fun UpdateClient.toMessage(id: String) = EditClientMessage(
    id = id,
    name = this.name,
    email = this.email
)