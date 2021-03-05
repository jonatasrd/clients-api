package br.com.luizalabs.clientsapi.infrastructure.http.routing

import br.com.luizalabs.clientsapi.usecase.DeleteClientUseCase
import br.com.luizalabs.clientsapi.usecase.FindClientsUseCase
import br.com.luizalabs.clientsapi.usecase.RegisterClientUseCase
import br.com.luizalabs.clientsapi.usecase.SendUpdateClientUseCase
import com.ninjasquad.springmockk.MockkBean
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

/**
 * Unit test for [ClientController]
 */
@WebMvcTest(ClientController::class, excludeAutoConfiguration = [SecurityAutoConfiguration::class])
internal class ClientControllerTest {

    @MockkBean
    lateinit var registerClientUseCase: RegisterClientUseCase

    @MockkBean
    lateinit var findClientsUseCase: FindClientsUseCase

    @MockkBean
    lateinit var sendUpdateClientUseCase: SendUpdateClientUseCase

    @MockkBean
    lateinit var deleteClientUseCase: DeleteClientUseCase

    private val baseUri: String = "/api/clients"
    private val baseUpdateAndDeleteUri: String = "/api/clients/{id}"
}