package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.repository.ClientMongoRepository
import org.springframework.stereotype.Service

@Service
class DeleteClientUseCase(
    val clientMongoRepository: ClientMongoRepository
) {

    fun execute(id: String) {
        clientMongoRepository.deleteById(id)
    }
}