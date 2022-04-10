package me.lonelyday.derpibooru.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import me.lonelyday.derpibooru.db.vo.Image
import me.lonelyday.derpibooru.db.vo.ImageWithTags
import me.lonelyday.derpibooru.repository.Repository
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    val featuredImageWithTags: MutableStateFlow<ImageWithTags?> = MutableStateFlow(null)

    fun loadFeaturedImage() {
        viewModelScope.launch {
            featuredImageWithTags.value = repo.featuredImage()
        }
    }
}