package me.lonelyday.derpibooru.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.lonelyday.derpibooru.db.vo.Image
import me.lonelyday.derpibooru.repository.Repository
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    fun loadFeaturedImage(): LiveData<Image> {
        val liveData = MutableLiveData<Image>()
        viewModelScope.launch {
//            liveData.value = repo.featuredImage()
        }
        return liveData
    }
}