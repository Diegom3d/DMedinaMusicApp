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
                println("→ Cargando lista de álbumes desde la API")
                val response = RetrofitInstance.api.getAlbums()
                _albums.value = response
                println("→ Lista de álbumes cargada: ${response.size} álbumes")
            } catch (e: Exception) {
                println("→ Error al cargar álbumes: ${e.javaClass.simpleName} - ${e.message}")
                _albums.value = emptyList()
            }
        }
    }

    fun loadAlbumById(id: String) {
        viewModelScope.launch {
            println("→ Ejecutando loadAlbumById con ID: $id")
            try {
                val album = RetrofitInstance.api.getAlbumById(id)

                if (album.title.isBlank() || album.artist.isBlank()) {
                    println("→ Álbum recibido pero incompleto: $album")
                    _selectedAlbum.value = Album(
                        id = id,
                        title = "Álbum no disponible",
                        artist = "Desconocido",
                        description = "No se pudo cargar la información del álbum.",
                        image = "",
                        tracks = emptyList()
                    )
                } else {
                    println("→ Álbum recibido: ${album.title}")
                    _selectedAlbum.value = album
                }

            } catch (e: Exception) {
                println("→ Error al cargar álbum: ${e.javaClass.simpleName} - ${e.message}")
                _selectedAlbum.value = Album(
                    id = id,
                    title = "Error al cargar",
                    artist = "Desconocido",
                    description = "Hubo un problema al obtener los datos del álbum.",
                    image = "",
                    tracks = emptyList()
                )
            }
        }
    }
}