package br.com.luizalabs.clientsapi.infrastructure.http.routing

import br.com.luizalabs.clientsapi.domain.Client
import br.com.luizalabs.clientsapi.domain.toDomain
import br.com.luizalabs.clientsapi.infrastructure.http.presentation.request.CreateClientRequest
import br.com.luizalabs.clientsapi.infrastructure.http.presentation.request.EditClientRequest
import br.com.luizalabs.clientsapi.infrastructure.http.presentation.request.toDomain
import br.com.luizalabs.clientsapi.infrastructure.http.presentation.response.ClientCreateResponse
import br.com.luizalabs.clientsapi.infrastructure.http.presentation.response.ClientResponse
import br.com.luizalabs.clientsapi.infrastructure.http.presentation.response.toCreateResponse
import br.com.luizalabs.clientsapi.infrastructure.http.presentation.response.toResponse
import br.com.luizalabs.clientsapi.usecase.FindClientByIdUseCase
import br.com.luizalabs.clientsapi.usecase.FindClientsUseCase
import br.com.luizalabs.clientsapi.usecase.RegisterClientUseCase
import br.com.luizalabs.clientsapi.usecase.SendAddToWishListUseCase
import br.com.luizalabs.clientsapi.usecase.SendDeleteClientUseCase
import br.com.luizalabs.clientsapi.usecase.SendRemoveFromWishListUseCase
import br.com.luizalabs.clientsapi.usecase.SendUpdateClientUseCase
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.NotEmpty


/**
 * Client rest controller.
 */
@Api(tags = ["Client controller"])
@RestController
@RequestMapping("/api/clients")
@Validated
class ClientController(
    val registerClientUseCase: RegisterClientUseCase,
    val findClientsUseCase: FindClientsUseCase,
    val sendUpdateClientUseCase: SendUpdateClientUseCase,
    val sendDeleteClientUseCase: SendDeleteClientUseCase,
    val sendAddToWishListUseCase: SendAddToWishListUseCase,
    val sendRemoveFromWishListUseCase: SendRemoveFromWishListUseCase,
    val findClientByIdUseCase: FindClientByIdUseCase
) {

    val logger: Logger = LoggerFactory.getLogger(javaClass)

    /**
     * Post method to register new client.
     *
     * @param request is a [CreateClientRequest]
     */
    @ApiOperation(value = "Register new Client")
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Validated request: CreateClientRequest): ClientCreateResponse {
        logger.info("CONTROLLER BEGIN | client create, request={}", request)

        val response = registerClientUseCase.execute(request.toDomain()).toCreateResponse()

        logger.info("CONTROLLER END | client create, response={}", response)

        return response
    }

    /**
     * Get method to return Clients.
     */
    @ApiOperation(value = "List Clients")
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findClients(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
    ): List<Client> {
        logger.info("CONTROLLER BEGIN | find clients")

        val paging: Pageable = PageRequest.of(page, 100)

        val response = findClientsUseCase.execute(paging)

        logger.info("CONTROLLER END | find clients")

        return response
    }

    /**
     * Get method to return a client.
     */
    @ApiOperation(value = "Find client by id")
    @GetMapping(value = ["/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findById(
        @PathVariable(name = "id") id: String
    ): ClientResponse {
        logger.info("CONTROLLER BEGIN | find client by id")

        val response = findClientByIdUseCase.execute(id).toDomain().toResponse()

        logger.info("CONTROLLER END | find client by id")

        return response
    }

    /**
     * Put method to update a client.
     *
     * @param request is a [EditClientRequest]
     */
    @ApiOperation(value = "Update a Client")
    @PutMapping(
        value = ["/{id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun edit(
        @PathVariable(name = "id") id: String,
        @RequestBody @Validated request: EditClientRequest) {
        logger.info("CONTROLLER BEGIN | client edit, request={}", request)

        sendUpdateClientUseCase.execute(id, request.toDomain())

        logger.info("CONTROLLER END | client edit")
    }

    /**
     * Delete method to delete a client.
     *
     * @param id is the client id
     */
    @ApiOperation(value = "Delete a Client")
    @DeleteMapping(
        value = ["/{id}"]
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun delete(
        @PathVariable(name = "id") id: String
    ) {
        logger.info("CONTROLLER BEGIN | client delete id={}", id)

        sendDeleteClientUseCase.execute(id)

        logger.info("CONTROLLER END | client delete")

    }

    /**
     * Post method to add products to client's wishlist.
     *
     * @param request is a [CreateClientRequest]
     */
    @ApiOperation(value = "Add products to wishlist")
    @PostMapping(
        value = ["/{id}/wishlist"],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun addProduct(@RequestBody @NotEmpty request: Set<String>) {
        logger.info("CONTROLLER BEGIN | client add product, request={}", request)

        sendAddToWishListUseCase.execute(request)

        logger.info("CONTROLLER END | client add product")

    }

    /**
     * Delete method to remove products to client's wishlist.
     *
     * @param request is a [CreateClientRequest]
     */
    @ApiOperation(value = "Delete products from wishlist")
    @DeleteMapping(
        value = ["/{id}/wishlist"],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun deleteProduct(@RequestBody @NotEmpty request: Set<String>) {
        logger.info("CONTROLLER BEGIN | client delete product, request={}", request)

        sendRemoveFromWishListUseCase.execute(request)

        logger.info("CONTROLLER END | client delete product")

    }

}