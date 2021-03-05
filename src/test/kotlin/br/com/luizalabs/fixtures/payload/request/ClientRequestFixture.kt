package br.com.luizalabs.fixtures.payload.request

import br.com.luizalabs.fixtures.consts.ClientConsFixture

object ClientRequestFixture {

    val DEFAULT = """
            {
                "name": "${ClientConsFixture.DEFAULT_NAME}",
                "email": "${ClientConsFixture.DEFAULT_EMAIL}"
            }
        """.trimIndent()

    val WISHLIST = """
            [
                "${ClientConsFixture.DEFAULT_ID_PRODUCT}"
            ]
        """.trimIndent()

    val REQUEST_WITHOUT_NAME = """
            {
                "email": "${ClientConsFixture.DEFAULT_EMAIL}"
            }
        """.trimIndent()

    val REQUEST_WITHOUT_EMAIL = """
            {
                "name": "${ClientConsFixture.DEFAULT_NAME}"
            }
        """.trimIndent()

    val REQUEST_INVALID_EMAIL = """
            {
                "name": "${ClientConsFixture.DEFAULT_NAME}",
                "email": "${ClientConsFixture.INVALID_EMAIL}"
            }
        """.trimIndent()


}