package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.DeleteClientMessage
import br.com.luizalabs.clientsapi.domain.DeleteClientPublisher
import org.springframework.stereotype.Service

@Service
class SendDeleteClientUseCase(
    val deleteClientPublisher: DeleteClientPublisher
) {

    fun execute(id: String) = deleteClientPublisher.publish(DeleteClientMessage(id))

}