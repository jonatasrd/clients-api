package br.com.luizalabs.clientsapi.domain

data class EditClientMessage(
    val id: String,
    val name: String?,
    val email: String?
)
