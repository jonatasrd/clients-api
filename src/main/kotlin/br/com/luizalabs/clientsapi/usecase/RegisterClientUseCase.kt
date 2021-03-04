package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.Client

class RegisterClientUseCase {

    fun execute(client: Client): Client {
        return Client("mock", "mock", emptySet())
    }
}