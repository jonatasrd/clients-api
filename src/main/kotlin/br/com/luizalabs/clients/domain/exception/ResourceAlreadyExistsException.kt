package br.com.luizalabs.clients.domain.exception

import br.com.luizalabs.clients.domain.exception.model.MessageError

/**
 * Exception for resource already exists
 *
 * @constructor Creates a ResourceAlreadyExistsException with default enum message
 */
class ResourceAlreadyExistsException : RuntimeException {

    constructor() : super(MessageError.UNPROCESSABLE_ENTITY.msg)

    /**
     * Overloaded ResourceAlreadyExistsException constructor with a new message
     *
     * @constructor Creates ResourceAlreadyExistsException with a message param
     */
    constructor(msg: String) : super(msg)


}