package br.com.luizalabs.clientsapi.infrastructure.rabbitmq.config

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RabbitMQExceptionHandler(
    val retries: Int = 1,
    val routing: String,
    val deadLetter: String,
    val exchange: String,
    val delay: Int = 60000,
    val exceptions: Array<KClass<out Throwable>> = [Exception::class]

)

