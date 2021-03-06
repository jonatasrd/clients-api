package br.com.luizalabs.clientsapi.repository

import br.com.luizalabs.fixtures.consts.ClientConsFixture
import br.com.luizalabs.fixtures.model.ClientMongoModelFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.MongoTemplate
import br.com.luizalabs.fixtures.model.ClientMongoModelFixture.Companion as Fixture

@DataMongoTest
class ClientMongoRepositoryTest @Autowired constructor(
    private val mongoTemplate: MongoTemplate,
    private val repository: ClientMongoRepository
) {

    @BeforeEach
    fun setup() {
        repository.deleteAll()
        mongoTemplate.save(Fixture.default())
    }

    @Test
    fun `Should save new client successfully and define an ID`() {
        val result = repository.save(Fixture.toSave())
        assertNotNull(result._id)
    }

    @Test
    fun `Should update register when the ID already exists`() {
        val result = repository.save(Fixture.withSameId())
        assertEquals(ClientConsFixture.OTHER_NAME, result.name)
        assertEquals(ClientConsFixture.DEFAULT_ID_PRODUCT, result.wishlist.first())
    }

    @Test
    fun `Should throw DuplicateKeyException when try to save with duplicate email`() {
       Assertions.assertThrows(DuplicateKeyException::class.java) {
            repository.save(Fixture.withSameEmail())
        }
    }

    @Test
    fun `Should return a list of ClientMongoModel using pagination`() {

        val result = repository.findAll(PageRequest.of(0, 100)).toList()

        assertThat(result).containsExactlyElementsOf(listOf(Fixture.default()))
    }

    @Test
    fun `Should return a ClientMongoModel using findById`() {

        val result = repository.findById(ClientConsFixture.DEFAULT_ID)

        assertEquals(result.get(), ClientMongoModelFixture.default())
    }

}