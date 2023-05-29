package com.aminprojects.androidencryptdycrept


import android.util.Base64
import java.math.BigInteger
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class AesEncryptManager {

    companion object {
        private const val KEY_ALGORITHM_AES = "AES"
        const val HEX_RADIX = 16
    }

    fun decryptWithAes(encryptedTextBytes: ByteArray, secretBytes: ByteArray): ByteArray {
        val secretKey = SecretKeySpec(secretBytes, KEY_ALGORITHM_AES)
        val cipher = Cipher.getInstance("AES/ECB/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        return cipher.doFinal(encryptedTextBytes)
    }

    fun encryptWithAes(textToEncrypt: String, secretBytes: ByteArray): ByteArray {
        val srcBuff = textToEncrypt.toByteArray(charset("UTF8"))
        val secretKey = SecretKeySpec(secretBytes, KEY_ALGORITHM_AES)
        val cipher: Cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher.doFinal(srcBuff)
    }
}

fun String.decodeBase64(): ByteArray = Base64.decode(this, Base64.NO_WRAP)

fun String.decodeHex(): ByteArray = chunked(2).map { it.toInt(AesEncryptManager.HEX_RADIX).toByte() }.toByteArray()

infix fun String.xor(that: String): String =
    String.format("%06x", BigInteger(this, AesEncryptManager.HEX_RADIX).xor(BigInteger(that, AesEncryptManager.HEX_RADIX)))

fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

fun ByteArray.toBase64(): String = Base64.encodeToString(this, Base64.NO_WRAP)