package br.com.luizalabs.clientsapi.repository.model

import br.com.luizalabs.clientsapi.domain.EditClientMessage
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(value = "client")
data class ClientMongoModel(

    @Id
    val _id: String?,

    @Field("code")
    val name: String,

    @Field("email")
    @Indexed(unique = true)
    val email: String,

    @Field("wishlist")
    val wishlist: MutableSet<String> = mutableSetOf()

) {

    /**
     * Overloaded constructor without id.
     */
    constructor(
        name: String,
        email: String
    ) : this(
        _id = null,
        name = name,
        email = email
    )
}

internal fun ClientMongoModel.toUpdate(editMessage: EditClientMessage) = ClientMongoModel(
    _id = this._id,
    name = editMessage.name ?: this.name,
    email = editMessage.email ?: this.email,
    wishlist = this.wishlist
)
