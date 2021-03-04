package br.com.luizalabs.clientsapi.domain.exception

import br.com.luizalabs.clientsapi.domain.exception.model.MessageError

/**
 * Exception for resource not found
 *
 * @constructor Creates a ResourceNotFoundException with default enum message
 */
class ResourceNotFoundException : RuntimeException {

    constructor() : super(MessageError.RESOURCE_NOT_FOUND.msg)

    /**
     * Overloaded ResourceNotFoundException constructor with a new message
     *
     * @constructor Creates ResourceNotFoundException with a message param
     */
    constructor(msg: String) : super(msg)


}