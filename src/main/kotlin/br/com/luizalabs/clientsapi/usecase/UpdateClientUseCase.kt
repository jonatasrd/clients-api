package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.EditClientMessage
import br.com.luizalabs.clientsapi.repository.ClientMongoRepository
import br.com.luizalabs.clientsapi.repository.model.toUpdate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service

@Service
class UpdateClientUseCase(
    val clientMongoRepository: ClientMongoRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun execute(editMessage: EditClientMessage) {

        try {

            val result = clientMongoRepository.findById(editMessage.id)

            if (result.isPresent) {
                val clientMongoModel = result.get()

                val clientToUpdate = clientMongoModel.toUpdate(editMessage)

                clientMongoRepository.save(clientToUpdate)
            }

        } catch (error: DuplicateKeyException) {
            logger.info("Already exists a client with email: ${editMessage.email}.")
        }
    }
}