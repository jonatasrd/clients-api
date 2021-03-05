package br.com.luizalabs.clientsapi.domain

interface EditClientPublisher {

    fun publish(editMessage: EditClientMessage)
}