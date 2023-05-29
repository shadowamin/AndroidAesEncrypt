package com.aminprojects.androidencryptdycrept

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CryptAlertDialog(errorMessage: String, onDismissRequest: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismissRequest.invoke() },
        title = { Text(text = "Error") },
        text = { Text(text = errorMessage) },
        confirmButton = {
            Button(
                onClick = { onDismissRequest.invoke() },
            ) {
                Text(text = "Ok")
            }
        }
    )
}

@Preview
@Composable
fun DisplayCryptAlertDialog() {
    CryptAlertDialog(
        errorMessage = "error on decrypting message",
        onDismissRequest = {}
    )
}