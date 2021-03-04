package br.com.luizalabs.clients.domain.exception.model

/**
 * MessageError Enum for errors
 *
 * @constructor msg: Error message
 */
enum class MessageError(val msg: String) {

    RESOURCE_NOT_FOUND("Resource Not found"),
    UNPROCESSABLE_ENTITY("Resource already exists"),
    DATABASE_INFRASTRUCTURE_ERROR("An error has occurred in the database transactions")

}
