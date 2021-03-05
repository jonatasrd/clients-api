package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*
import kotlin.collections.HashMap

/**
 * RabbitMQ message domain.
 */
class RabbitMQMessage {

    lateinit var envelop: RabbitMQEnvelope<*>
    val headers: MutableMap<String, String> = HashMap()

    companion object {

        private val mapper = ObjectMapper()
        private val message: RabbitMQMessage = RabbitMQMessage()

        fun withEnvelop(envelop: RabbitMQEnvelope<*>): Companion {
            message.envelop = envelop
            return this
        }

        fun withHeader(key: String, value: Any): Companion {
            Objects.requireNonNull(value)
            message.headers[key] = toHeaderString(value)
            return this
        }

        fun withEncodedHeader(key: String, value: Any): Companion {
            Objects.requireNonNull(value)
            message.headers[key] = Base64.getEncoder().encodeToString(toHeaderString(value).toByteArray())
            return this
        }

        fun build(): RabbitMQMessage {
            return message
        }

        private fun toHeaderString(value: Any): String {
            return if (value.javaClass.isPrimitive) {
                value.toString()
            } else {
                writeAsJson(value)
            }
        }

        private fun writeAsJson(value: Any): String {
            return try {
                mapper.writeValueAsString(value)
            } catch (e: JsonProcessingException) {
                throw IllegalStateException(e)
            }

        }

    }
}