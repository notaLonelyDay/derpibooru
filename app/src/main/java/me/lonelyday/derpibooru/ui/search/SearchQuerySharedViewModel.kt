package me.lonelyday.derpibooru.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.lonelyday.api.models.Query

class SearchQuerySharedViewModel : ViewModel() {
    val query: MutableLiveData<Query> = MutableLiveData()
}