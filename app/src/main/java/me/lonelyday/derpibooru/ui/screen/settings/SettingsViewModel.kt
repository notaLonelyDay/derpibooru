package me.lonelyday.derpibooru.ui.screen.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.lonelyday.derpibooru.repository.SettingsRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val key = MutableStateFlow(settingsRepository.key ?: "")

    fun setKey(key: String) {
        settingsRepository.key = key
        this.key.value = key
    }
}