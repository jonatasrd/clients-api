package br.com.luizalabs.clientsapi.domain

interface DeleteClientPublisher {

    fun publish(deleteMessage: DeleteClientMessage)
}