package br.com.luizalabs.clients.infrastructure.http.routing

import br.com.luizalabs.clients.infrastructure.http.presentation.request.CreateClientRequest
import br.com.luizalabs.clients.infrastructure.http.presentation.request.toDomain
import br.com.luizalabs.clients.infrastructure.http.presentation.response.ClientResponse
import br.com.luizalabs.clients.infrastructure.http.presentation.response.toResponse
import br.com.luizalabs.clients.usecase.DeleteClientUseCase
import br.com.luizalabs.clients.usecase.FindClientsUseCase
import br.com.luizalabs.clients.usecase.RegisterClientUseCase
import br.com.luizalabs.clients.usecase.UpdateClientUseCase
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * Client rest controller.
 */
@Api(tags = ["Client controller"])
@RestController
@RequestMapping("/api/clients")
class ClientsController(
    val registerClientUseCase: RegisterClientUseCase,
    val findClientsUseCase: FindClientsUseCase,
    val updateClientUseCase: UpdateClientUseCase,
    val deleteClientUseCase: DeleteClientUseCase
) {

    val logger: Logger = LoggerFactory.getLogger(javaClass)

    /**
     * Post method to register new client.
     *
     * @param request is a [CreateClientRequest]
     */
    @ApiOperation(value = "Register new Client")
    @ApiResponses(
        ApiResponse(
            code = 400,
            message = "Bad Request (e.g. payload validation)"
        ),
        ApiResponse(
            code = 422,
            message = "Unprocessable Entity (e.g. client email already exists)"
        )
    )
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(code = HttpStatus.CREATED)
    fun create(@RequestBody @Validated request: CreateClientRequest): ClientResponse {
        logger.info("CONTROLLER BEGIN | client create, request={}", request)

        val response = registerClientUseCase.execute(request.toDomain()).toResponse()

        logger.info("CONTROLLER END | client create, response={}", response)

        return response
    }
}