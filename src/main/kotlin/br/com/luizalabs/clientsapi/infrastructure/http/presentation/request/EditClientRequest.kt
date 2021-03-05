package br.com.luizalabs.clientsapi.infrastructure.http.presentation.request

import br.com.luizalabs.clientsapi.domain.Client
import br.com.luizalabs.clientsapi.domain.UpdateClient
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class EditClientRequest(

    @field:NotBlank
    @ApiModelProperty(
        value = "A name for Client",
        required = false,
        example = "Jonatas Ramos"
    )
    val name: String? = null,

    @field:NotBlank
    @field:Email
    @ApiModelProperty(
        value = "Valid email for Client",
        required = false,
        example = "jonatasrd@gmail.com"
    )
    val email: String? = null
)

/**
 * Convert [CreateClientRequest] to [Client] domain.
 */
internal fun EditClientRequest.toDomain() = UpdateClient(
    name = this.name?.toUpperCase(),
    email = this.email?.toLowerCase()
)