package br.com.luizalabs.clientsapi.infrastructure.http.presentation.request

import br.com.luizalabs.clientsapi.domain.Client
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class CreateClientRequest(

    @field:NotBlank
    @ApiModelProperty(
        value = "A name for Client",
        required = true,
        example = "Jonatas Ramos"
    )
    val name: String = "",

    @field:NotBlank
    @field:Email
    @ApiModelProperty(
        value = "Valid email for Client",
        required = true,
        example = "jonatasrd@gmail.com"
    )
    val email: String = ""
)

/**
 * Convert [CreateClientRequest] to [Client] domain.
 */
internal fun CreateClientRequest.toDomain() = Client(
    name = this.name.toUpperCase(),
    email = this.email,
    wishlist = emptySet()
)
