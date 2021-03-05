package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.repository.ClientMongoRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DeleteClientUseCase(
    val clientMongoRepository: ClientMongoRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun execute(id: String) {
        clientMongoRepository.deleteById(id)
    }
}