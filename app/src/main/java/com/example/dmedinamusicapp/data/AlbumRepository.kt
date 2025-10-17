package com.example.dmedinamusicapp.data

class AlbumRepository {
    suspend fun fetchAlbums(): List<Album> {
        return try {
            RetrofitInstance.api.getAlbums()
        } catch (e: Exception) {
            println("→ Error al obtener álbumes: ${e.message}")
            emptyList()
        }
    }

    suspend fun fetchAlbumById(id: String): Album {
        return try {
            RetrofitInstance.api.getAlbumById(id)
        } catch (e: Exception) {
            println("→ Error al obtener álbum por ID: ${e.message}")
            Album(
                id = id,
                title = "Desconocido",
                artist = "Desconocido",
                description = "No se pudo cargar el álbum.",
                image = "",
                tracks = emptyList()
            )
        }
    }
}