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
fun DecryptScreen(
    cryptManager: AesEncryptManager
) {
    AndroidEncryptDycreptTheme {
        val encryptedText = remember { mutableStateOf("") }
        val selectedTextType = remember { mutableStateOf(BASE_64) }
        val secretText = remember { mutableStateOf("") }
        val secretTextType = remember { mutableStateOf(BASE_64) }
        val decryptedText = remember { mutableStateOf("") }
        val errorDialogDisplayMessage = remember { mutableStateOf("") }
        val errorDialogDisplay = remember { mutableStateOf(false) }
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Encrypted Text", style = MaterialTheme.typography.titleLarge)
                    Row(modifier = Modifier.weight(0.5F), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = selectedTextType.value == BASE_64, onClick = {
                            selectedTextType.value = BASE_64
                        })
                        Text(text = BASE_64)
                    }
                    Row(modifier = Modifier.weight(0.5F), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = selectedTextType.value == HEX, onClick = {
                            selectedTextType.value = HEX
                        })
                        Text(text = HEX)
                    }
                }
                TextField(modifier = Modifier.fillMaxWidth(), value = encryptedText.value, onValueChange = { encryptedText.value = it })
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
                Spacer(modifier = Modifier.height(24.dp))
                Text("Decrypted Text", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(12.dp))
                TextField(modifier = Modifier.fillMaxWidth(), value = decryptedText.value, onValueChange = {})
                Spacer(modifier = Modifier.weight(1.0f))
                Button(
                    shape = RectangleShape,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        try {
                            decryptedText.value = String(
                                cryptManager.decryptWithAes(
                                    secretBytes = transformToByte(
                                        type = secretTextType.value,
                                        value = secretText.value
                                    ),
                                    encryptedTextBytes = transformToByte(
                                        type = selectedTextType.value,
                                        value = encryptedText.value
                                    )
                                ), Charsets.UTF_8
                            )
                        } catch (e: Exception) {
                            errorDialogDisplayMessage.value = e.message.toString()
                            errorDialogDisplay.value = true
                        }
                    }
                ) {
                    Text("Decrypt", style = MaterialTheme.typography.titleLarge)
                }
            }
            if (errorDialogDisplay.value) {
                CryptAlertDialog(errorMessage = errorDialogDisplayMessage.value) {
                    errorDialogDisplay.value = false
                }
            }
        }
    }
}

@Preview
@Composable
fun DisplayDecryptScreen() {
    DecryptScreen(
        cryptManager = AesEncryptManager()
    )
}