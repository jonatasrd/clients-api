package br.com.luizalabs.clientsapi.usecase

import br.com.luizalabs.clientsapi.domain.toDomain
import br.com.luizalabs.clientsapi.repository.ClientMongoRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class FindClientsUseCase(
    val clientMongoRepository: ClientMongoRepository
) {

    fun execute(paging: Pageable) = clientMongoRepository.findAll(paging).toList().map { it.toDomain() }
}
