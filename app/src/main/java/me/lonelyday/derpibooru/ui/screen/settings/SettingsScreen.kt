package me.lonelyday.derpibooru.ui.screen.settings

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.alorma.compose.settings.ui.SettingsMenuLink

@Composable
@Preview
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val key by settingsViewModel.key.collectAsState()
    SettingsMenuClickable(title = "ApiKey", text = key, onConfirm = { settingsViewModel.setKey(it) })
}

@Composable
fun SettingsMenuClickable(
    title: String,
    text: String,
    onConfirm: (String) -> Unit
) {
    val isDialogShown = remember { mutableStateOf(false) }
    SettingsMenuLink(title = { Text(text = title) }) {
        isDialogShown.value = true
    }
    if (isDialogShown.value) {
        var dialogText by remember { mutableStateOf(text) }

        AlertDialog(
            text = {
                OutlinedTextField(
                    value = dialogText,
                    onValueChange = { dialogText = it },
                    label = { Text(title) }
                )
            },
            onDismissRequest = { isDialogShown.value = false },
            dismissButton = {
                Button(onClick = { isDialogShown.value = false }) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                Button(onClick = {
                    onConfirm(dialogText)
                    isDialogShown.value = false
                }) {
                    Text(text = "Confirm")
                }
            }
        )
    }

}

@Composable
fun EditableDialog(
) {

}