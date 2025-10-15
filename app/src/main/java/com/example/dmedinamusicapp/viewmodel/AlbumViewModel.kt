package com.example.dmedinamusicapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dmedinamusicapp.data.Album
import com.example.dmedinamusicapp.data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlbumViewModel : ViewModel() {
    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums

    private val _selectedAlbum = MutableStateFlow<Album?>(null)
    val selectedAlbum: StateFlow<Album?> = _selectedAlbum

    init {
        viewModelScope.launch {
            try {
                _albums.value = RetrofitInstance.api.getAlbums()
            } catch (e: Exception) {

            }
        }
    }

    fun loadAlbumById(id: String) {
        viewModelScope.launch {
            try {
                _selectedAlbum.value = RetrofitInstance.api.getAlbumById(id)
            } catch (e: Exception) {
                _selectedAlbum.value = null
            }
        }
    }
}