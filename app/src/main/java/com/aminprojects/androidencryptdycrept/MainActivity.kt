package com.aminprojects.androidencryptdycrept

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {

    companion object{
        const val BASE_64 = "Base64"
        const val HEX = "Hex"

        fun transformToByte(type: String, value: String) =
            when (type) {
                HEX -> value.decodeHex()
                BASE_64 -> value.decodeBase64()
                else -> value.toByteArray()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AesPagerScreen()
        }
    }

}
