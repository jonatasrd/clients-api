package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config

/**
 * RabbitMQ consts configuration.
 */
object RabbitMQConstDefinition {

    const val CLIENT_EDIT_EXCHANGE = "client_edit_exchange"
    const val CLIENT_EDIT_QUEUE = "client_edit_queue"
    const val CLIENT_EDIT_DLQ = "client_edit_dead_letter"

    const val CLIENT_DELETE_EXCHANGE = "client_delete_exchange"
    const val CLIENT_DELETE_QUEUE = "client_delete_queue"
    const val CLIENT_DELETE_DLQ = "client_delete_dead_letter"

    const val CLIENT_ADD_WISHLIST_EXCHANGE = "add_wishlist_exchange"
    const val CLIENT_ADD_WISHLIST_QUEUE = "add_wishlist_queue"
    const val CLIENT_ADD_WISHLIST_DLQ = "add_wishlist_dead_letter"

    const val CLIENT_REMOVE_WISHLIST_EXCHANGE = "remove_wishlist_exchange"
    const val CLIENT_REMOVE_WISHLIST_QUEUE = "remove_wishlist_queue"
    const val CLIENT_REMOVE_WISHLIST_DLQ = "remove_wishlist_dead_letter"

}