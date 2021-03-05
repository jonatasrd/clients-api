package br.com.luizalabs.fixtures.payload.response

import br.com.luizalabs.fixtures.consts.ClientConsFixture

object ClientResponseFixture {

    val DEFAULT = """
            {
                "id": "${ClientConsFixture.DEFAULT_ID}",
                "name": "${ClientConsFixture.DEFAULT_NAME}",
                "email": "${ClientConsFixture.DEFAULT_EMAIL}"
            }
        """.trimIndent()

    val DEFAULT_LIST = """
            [{
                "id": "${ClientConsFixture.DEFAULT_ID}",
                "name": "${ClientConsFixture.DEFAULT_NAME}",
                "email": "${ClientConsFixture.DEFAULT_EMAIL}"
            }]
        """.trimIndent()


    val ERROR_WITHOUT_NAME = """
            {
                "status": 400,
                "error": "BAD_REQUEST",
                "message": [
                    "Field name: must not be blank"
                ],
                "timestamp": x
            }
        """.trimIndent()

    val ERROR_WITHOUT_EMAIL = """
            {
                "status": 400,
                "error": "BAD_REQUEST",
                "message": [
                    "Field email: must not be blank"
                ],
                "timestamp": x
            }
        """.trimIndent()

    val ERROR_INVALID_EMAIL = """
            {
                "status": 400,
                "error": "BAD_REQUEST",
                "message": [
                    "Field email: must be a well-formed email address"
                ],
                "timestamp": x
            }
        """.trimIndent()

    val EMPTY_LIST = """
            []
        """.trimIndent()

}