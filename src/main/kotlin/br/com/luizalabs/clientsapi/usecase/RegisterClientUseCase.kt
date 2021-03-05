package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.Client
import br.com.luizalabs.clientsapi.domain.exception.ResourceAlreadyExistsException
import br.com.luizalabs.clientsapi.domain.toDomain
import br.com.luizalabs.clientsapi.domain.toNewMongoModel
import br.com.luizalabs.clientsapi.repository.ClientMongoRepository
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service

@Service
class RegisterClientUseCase(
    val clientMongoRepository: ClientMongoRepository
) {

    fun execute(client: Client): Client {
        return try {
            clientMongoRepository.save(client.toNewMongoModel()).toDomain()
        } catch (error: DuplicateKeyException) {
            throw ResourceAlreadyExistsException("Already exists a client with email: ${client.email}.")
        }
    }
}