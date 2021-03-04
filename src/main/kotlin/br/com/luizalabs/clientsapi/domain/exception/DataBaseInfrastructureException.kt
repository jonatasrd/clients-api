package br.com.luizalabs.clientsapi.domain.exception

import br.com.luizalabs.clientsapi.domain.exception.model.MessageError

/**
 * Exception for database errors
 *
 * @constructor Creates a DataBaseInfrastructureException with default enum message
 */
class DataBaseInfrastructureException : RuntimeException {

    constructor() : super(MessageError.DATABASE_INFRASTRUCTURE_ERROR.msg)

    /**
     * Overloaded DataBaseInfrastructureException constructor with a new message
     *
     * @constructor Creates DataBaseInfrastructureException with a message param
     */
    constructor(msg: String) : super(msg)


}