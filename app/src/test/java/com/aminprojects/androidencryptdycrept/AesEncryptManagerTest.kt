package com.aminprojects.androidencryptdycrept

import junit.framework.TestCase.assertEquals
import org.junit.Test

class AesEncryptManagerTest {

    companion object {
        private const val SECRET_KEY = "c007c8c4e322bb3c6cbf6dbc41627651"
    }

    private val aesEncryptManager = AesEncryptManager()

    @Test
    fun encryptWithAes() {
        val result = aesEncryptManager.encryptWithAes(
            textToEncrypt = "Hello world",
            secretBytes = SECRET_KEY.decodeHex()
        ).toHex()
        assertEquals(result, "9f9cb2b69176dd5978bba2ae6d32273d")
    }

    @Test
    fun decryptWithAes() {
        val result = String(
            aesEncryptManager.decryptWithAes(
                encryptedTextBytes = "9f9cb2b69176dd5978bba2ae6d32273d".decodeHex(),
                secretBytes = SECRET_KEY.decodeHex()
            ), Charsets.UTF_8
        ).replace("\u0005","")
        assertEquals(result, "Hello world")
    }
}