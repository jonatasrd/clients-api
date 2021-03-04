package br.com.luizalabs.clientsapi.domain.exception.handler

import br.com.luizalabs.clientsapi.domain.exception.DataBaseInfrastructureException
import br.com.luizalabs.clientsapi.domain.exception.ResourceAlreadyExistsException
import br.com.luizalabs.clientsapi.domain.exception.ResourceNotFoundException
import br.com.luizalabs.clientsapi.domain.exception.model.ResponseError
import org.hibernate.validator.internal.engine.path.PathImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime
import javax.validation.ConstraintViolationException

/**
 * Class for exception handler
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class GlobalExceptionHandler {

    val log: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handlerHttpRequestMethodNotSupportedException(ex: Exception) = let {

        val error = ResponseError(
            METHOD_NOT_ALLOWED.value(),
            METHOD_NOT_ALLOWED.name,
            ex.message.orEmpty(),
            LocalDateTime.now()
        )

        log.debug("Error:  $error")

        ResponseEntity(error, METHOD_NOT_ALLOWED)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handlerResourceNotFoundException(ex: ResourceNotFoundException) = let {

        val error = ResponseError(
            NOT_FOUND.value(),
            NOT_FOUND.name,
            ex.message.orEmpty(),
            LocalDateTime.now()
        )

        log.debug("Error:  $error")

        ResponseEntity(error, NOT_FOUND)
    }

    @ExceptionHandler(ResourceAlreadyExistsException::class)
    fun handlerResourceNotFoundException(ex: ResourceAlreadyExistsException) = let {

        val error = ResponseError(
            UNPROCESSABLE_ENTITY.value(),
            UNPROCESSABLE_ENTITY.name,
            ex.message.orEmpty(),
            LocalDateTime.now()
        )

        log.debug("Error:  $error")

        ResponseEntity(error, UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException) = let {

        val bindingResultErrors = ex.bindingResult.fieldErrors
        val errors = ResponseError(
            BAD_REQUEST.value(),
            BAD_REQUEST.name,
            mutableListOf(),
            LocalDateTime.now()
        )

        bindingResultErrors.map {
            "Field ${it.field}: ${it.defaultMessage.orEmpty()}"
        }.sorted().forEach { errors.add(it) }

        log.debug("Error:  $errors")

        ResponseEntity(errors, BAD_REQUEST)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationExceptions(ex: ConstraintViolationException) = let {

        val constraintViolations = ex.constraintViolations
        val errors = ResponseError(
            BAD_REQUEST.value(),
            BAD_REQUEST.name,
            mutableListOf(),
            LocalDateTime.now()
        )

        constraintViolations.map { error ->
            val pathImpl = error.propertyPath as PathImpl
            "Field ${pathImpl.leafNode.name}[${pathImpl.leafNode.index}]: ${error.message.orEmpty()}"
        }.sortedWith(
            compareBy(
                { it.substring(it.indexOf("["), it.indexOf("]")) },
                { it.substring(0, it.indexOf("]")) },
                { it.substring(it.indexOf("]")) }
            )
        ).forEach {
            errors.add(it)
        }

        log.debug("Error:  $errors")

        ResponseEntity(errors, BAD_REQUEST)
    }

    @ExceptionHandler(DataBaseInfrastructureException::class)
    fun handlerDataBaseInfrastructureException(ex: DataBaseInfrastructureException) = let {

        val error = ResponseError(
            INTERNAL_SERVER_ERROR.value(),
            INTERNAL_SERVER_ERROR.name,
            ex.message.orEmpty(),
            LocalDateTime.now()
        )

        log.debug("Error:  $error")

        ResponseEntity(error, INTERNAL_SERVER_ERROR)
    }
}
