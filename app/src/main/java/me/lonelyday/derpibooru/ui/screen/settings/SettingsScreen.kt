package me.lonelyday.derpibooru.ui.screen.settings

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.jamal.composeprefs.ui.PrefsScreen
import com.jamal.composeprefs.ui.prefs.TextPref

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun SettingsScreen() {
    PrefsScreen(dataStore = preferencesDataStore(name = "settings") as DataStore<Preferences>) {
        prefsItem { TextPref(title = "Just some text", summary = "Here is some summary text") }
    }
}

