package com.aminprojects.androidencryptdycrept

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aminprojects.androidencryptdycrept.MainActivity.Companion.BASE_64
import com.aminprojects.androidencryptdycrept.MainActivity.Companion.HEX
import com.aminprojects.androidencryptdycrept.MainActivity.Companion.transformToByte
import com.aminprojects.androidencryptdycrept.ui.theme.AndroidEncryptDycreptTheme
import java.lang.Exception

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EncryptScreen(
    cryptManager: AesEncryptManager
) {
    AndroidEncryptDycreptTheme {
        val textToEncrypt = remember { mutableStateOf("") }
        val secretText = remember { mutableStateOf("") }
        val secretTextType = remember { mutableStateOf(BASE_64) }
        val encryptedText = remember { mutableStateOf("") }
        val decryptedTextType = remember { mutableStateOf(BASE_64) }
        val errorDialogDisplayMessage = remember { mutableStateOf("") }
        val errorDialogDisplay = remember { mutableStateOf(false) }
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text("Text to encrypt", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(modifier = Modifier.fillMaxWidth(), value = textToEncrypt.value, onValueChange = { textToEncrypt.value = it })
                Spacer(modifier = Modifier.height(24.dp))
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Secret key", style = MaterialTheme.typography.titleLarge)
                    Row(modifier = Modifier.weight(0.5F), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = secretTextType.value == BASE_64, onClick = {
                            secretTextType.value = BASE_64
                        })
                        Text(text = BASE_64)
                    }
                    Row(modifier = Modifier.weight(0.5F), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = secretTextType.value == HEX, onClick = {
                            secretTextType.value = HEX
                        })
                        Text(text = HEX)
                    }
                }
                TextField(modifier = Modifier.fillMaxWidth(), value = secretText.value, onValueChange = { secretText.value = it })
                Spacer(modifier = Modifier.height(12.dp))

                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Encrypted Text", style = MaterialTheme.typography.titleLarge)
                    Row(modifier = Modifier.weight(0.5F), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = decryptedTextType.value == BASE_64, onClick = {
                            decryptedTextType.value = BASE_64
                        })
                        Text(text = BASE_64)
                    }
                    Row(modifier = Modifier.weight(0.5F), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = decryptedTextType.value == HEX, onClick = {
                            decryptedTextType.value = HEX
                        })
                        Text(text = HEX)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextField(modifier = Modifier.fillMaxWidth(), value = encryptedText.value, onValueChange = {})
                Spacer(modifier = Modifier.weight(1.0f))
                Button(
                    shape = RectangleShape,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        try {
                            val encrypted = cryptManager.encryptWithAes(
                                secretBytes = transformToByte(
                                    type = secretTextType.value,
                                    value = secretText.value
                                ),
                                textToEncrypt = textToEncrypt.value
                            )
                            encryptedText.value = if (decryptedTextType.value == BASE_64) {
                                encrypted.toBase64()
                            } else {
                                encrypted.toHex()
                            }
                        } catch (e: Exception) {
                            errorDialogDisplayMessage.value = e.message.toString()
                            errorDialogDisplay.value = true
                        }
                    }
                ) {
                    Text("Encrypt", style = MaterialTheme.typography.titleLarge)
                }
            }
            if(errorDialogDisplay.value){
                CryptAlertDialog(errorMessage = errorDialogDisplayMessage.value) {
                    errorDialogDisplay.value = false
                }
            }
        }
    }
}

@Preview
@Composable
fun DisplayEncryptScreen() {
    EncryptScreen(
        cryptManager = AesEncryptManager()
    )
}
