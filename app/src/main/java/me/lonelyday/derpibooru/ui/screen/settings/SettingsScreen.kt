package me.lonelyday.derpibooru.ui.screen.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.jamal.composeprefs.ui.PrefsScreen
import com.jamal.composeprefs.ui.prefs.TextPref

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun SettingsScreen() {

    var openedDialog by remember { mutableStateOf(false) }

    SettingsMenuLink(title = { Text(text = "Title") }) {
        openedDialog = true
    }

    if (openedDialog) {
        AlertDialog(
            title = { Text(text = "ASdasd") },
            text = {
                TextField(value = "asdasd", onValueChange = {})
            },
            onDismissRequest = {
                openedDialog = false
            },
            confirmButton = {
                Button(onClick = { /*TODO*/ }) {

                }
            }
        )
    }
}