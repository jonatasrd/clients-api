package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.isSuperclassOf


@Aspect
@Component
class RabbitMQExceptionHandlerAspect(val template: RabbitTemplate) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val HEADER_MESSAGE_DELAY = "x-delay"
    private val HEADER_X_DEATH = "x-death"


    @Throws(Throwable::class)
    @Around("@annotation(br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config.RabbitMQExceptionHandler)")
    fun getMessage(joinPoint: ProceedingJoinPoint): Any? {

        var envelop: RabbitMQEnvelope<*>? = null
        val annotation = extractAnnotation(joinPoint)
        try {
            envelop = extractMessageEnvelop(joinPoint)
            if (isValidRedeliveryCount(annotation, envelop)) {
                return joinPoint.proceed()
            }

            if (!hasDeadLetterConfigured(annotation)) {
                throw IllegalStateException(
                    "The message $envelop was delivery more than  ${annotation.retries} times"
                )
            }
            publishToDeadLetter(annotation, envelop)
        } catch (ex: Throwable) {
            logger.error("An unexpected error has occurred during the message listener processing.")
            if (!handleException(annotation, envelop!!, ex)) {
                throw ex
            }

        }
        return null
    }

    private fun isValidRedeliveryCount(annotation: RabbitMQExceptionHandler, envelop: RabbitMQEnvelope<*>): Boolean {
        return envelop.incrementDeliveryCount() < annotation.retries
    }

    private fun extractAnnotation(point: ProceedingJoinPoint): RabbitMQExceptionHandler {
        val signature = point.signature as MethodSignature
        return signature.method.getAnnotation(RabbitMQExceptionHandler::class.java)
    }

    private fun extractMessageEnvelop(point: ProceedingJoinPoint): RabbitMQEnvelope<*> {
        val argument: Any = point.args[0]
        if (argument !is RabbitMQEnvelope<*>) {
            throw IllegalArgumentException("The argument is required to be an instance of RabbitMQEnvelope")
        } else {
            return argument
        }
    }

    private fun hasDeadLetterConfigured(annotation: RabbitMQExceptionHandler) =
        annotation.deadLetter.isNotEmpty()

    private fun handleException(
        annotation: RabbitMQExceptionHandler,
        envelop: RabbitMQEnvelope<*>,
        throwable: Throwable
    ): Boolean {

        return when {
            isDelayableException(annotation, throwable) -> {
                publishToExchange(annotation, envelop)
                true
            }
            hasDeadLetterConfigured(annotation) -> {
                publishToDeadLetter(annotation, envelop)
                true
            }
            else -> {
                false
            }
        }

    }

    private fun isDelayableException(annotation: RabbitMQExceptionHandler, t: Throwable): Boolean {

        val var3: Array<KClass<out Throwable>> = annotation.exceptions
        val var4 = var3.size

        for (var5 in 0 until var4) {
            val clazz = var3[var5]
            if (clazz.isSuperclassOf(t::class)) {
                return true
            }
        }
        return false
    }

    private fun publishToExchange(annotation: RabbitMQExceptionHandler, envelop: RabbitMQEnvelope<*>) {
        logger.debug("Due the exception the message $envelop was sent to the exchange queue with delay time set.");

        envelop.incrementDeliveryCount();

        template.convertAndSend(annotation.exchange, annotation.routing, envelop) { processor: Message ->

            val headers = processor.messageProperties.headers
            headers[HEADER_MESSAGE_DELAY] = annotation.delay
            processor
        }

    }

    private fun publishToDeadLetter(annotation: RabbitMQExceptionHandler, envelop: RabbitMQEnvelope<*>) {
        logger.info("Publishing message to dead-letter ${annotation.deadLetter}")
        logger.debug("Due the exception or max redelivery count, the message $envelop was sent to dead letter")

        template.convertAndSend(annotation.exchange, annotation.deadLetter, envelop) { processor: Message ->
            val headers = processor.messageProperties.headers
            headers[HEADER_X_DEATH] = annotation.routing
            processor
        }

    }

}