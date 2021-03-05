package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.EditClientMessage
import br.com.luizalabs.clientsapi.domain.exception.ResourceNotFoundException
import br.com.luizalabs.clientsapi.repository.ClientMongoRepository
import br.com.luizalabs.clientsapi.repository.client.toUpdate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service

@Service
class UpdateClientUseCase(
    val findClientByIdUseCase: FindClientByIdUseCase,
    val clientMongoRepository: ClientMongoRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun execute(editMessage: EditClientMessage) {

        try {

            val oldClient = findClientByIdUseCase.execute(editMessage.id)

            val clientToUpdate = oldClient.toUpdate(editMessage)

            clientMongoRepository.save(clientToUpdate)

        } catch (error: DuplicateKeyException) {
            logger.info("Already exists a client with email: ${editMessage.email}.")

        } catch (error: ResourceNotFoundException) {
            logger.info(error.message)
        }
    }
}