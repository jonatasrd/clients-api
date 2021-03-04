package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.Client
import org.springframework.stereotype.Service

@Service
class RegisterClientUseCase {

    fun execute(client: Client): Client {
        return Client("mock", "mock", emptySet())
    }
}