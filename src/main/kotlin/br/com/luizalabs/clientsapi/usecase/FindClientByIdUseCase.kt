package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.exception.ResourceNotFoundException
import br.com.luizalabs.clientsapi.repository.ClientMongoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FindClientByIdUseCase(
    val clientMongoRepository: ClientMongoRepository
) {

    fun execute(id: String) = clientMongoRepository.findByIdOrNull(id)
        ?: throw ResourceNotFoundException("Client not found by id: $id!")
}