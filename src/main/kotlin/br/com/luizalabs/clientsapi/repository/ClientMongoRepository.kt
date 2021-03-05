package br.com.luizalabs.clientsapi.repository

import br.com.luizalabs.clientsapi.repository.model.ClientMongoModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Spring MongoRepository for ClientMongoModel.
 */
@Repository
interface ClientMongoRepository : MongoRepository<ClientMongoModel, String>
