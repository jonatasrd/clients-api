package br.com.luizalabs.clients.domain.exception.model

import java.time.LocalDateTime

/**
 * ResponseError class for accumulate errors
 */
data class ResponseError(
    val status: Int,
    val error: String,
    val message: MutableList<String>,
    val timestamp: LocalDateTime
) {
    /**
     * Overloaded constructor to transform object message to list.
     */
    constructor(
        status: Int,
        error: String,
        message: String,
        timestamp: LocalDateTime
    ) : this(
        status = status,
        error = error,
        message = listOf<String>(message).toMutableList(),
        timestamp = timestamp
    )

    /**
     * Add a message error to list
     *
     * @param error: A String
     */
    fun add(error: String) {
        message.run { add(error) }
    }
}
