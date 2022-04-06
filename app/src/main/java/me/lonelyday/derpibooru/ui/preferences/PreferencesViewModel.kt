package me.lonelyday.derpibooru.ui.preferences

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.lonelyday.derpibooru.repository.Repository
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    suspend fun checkKey(key: String): Boolean {
        return repo.checkKey(key)
    }
}