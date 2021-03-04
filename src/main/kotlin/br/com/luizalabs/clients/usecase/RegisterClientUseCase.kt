package br.com.luizalabs.clients.usecase

import br.com.luizalabs.clients.domain.Client

class RegisterClientUseCase {

    fun execute(client: Client): Client {
        return Client("mock", "mock", emptySet())
    }
}