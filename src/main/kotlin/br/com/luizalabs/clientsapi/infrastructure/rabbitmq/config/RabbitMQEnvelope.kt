package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

/**
 * RabbitMQ envelope with object and delivery count info.
 */
@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE
)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
open class RabbitMQEnvelope<T>(obj: T) : Serializable {

    private var deliveryCount: Int = 0

    @field:JsonProperty("object")
    var obj: T? = obj

    fun incrementDeliveryCount(): Int {
        deliveryCount += 1
        return deliveryCount
    }

    override fun toString(): String {
        return StringBuilder()
            .append("deliveryCount", deliveryCount)
            .append("object", obj)
            .toString()
    }

    companion object {
        fun <T> of(obj: T): RabbitMQEnvelope<T> {
            return RabbitMQEnvelope(obj)
        }
    }
}