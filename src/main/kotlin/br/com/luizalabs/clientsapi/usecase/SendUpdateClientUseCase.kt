package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.EditClientPublisher
import br.com.luizalabs.clientsapi.domain.UpdateClient
import br.com.luizalabs.clientsapi.domain.toMessage
import org.springframework.stereotype.Service

@Service
class SendUpdateClientUseCase(
    val editClientPublisher: EditClientPublisher
) {

    fun execute(id: String, clientToUpdate: UpdateClient) =
        editClientPublisher.publish(clientToUpdate.toMessage(id))

}