package br.com.luizalabs.clientsapi.repository

import br.com.luizalabs.clientsapi.repository.client.ClientMongoModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data Repository for Context.
 */
@Repository
interface ClientMongoRepository : MongoRepository<ClientMongoModel, String>
