package com.example.dmedinamusicapp.data

class AlbumRepository {
    suspend fun fetchAlbums(): List<Album> {
        return RetrofitInstance.api.getAlbums()
    }

    suspend fun fetchAlbumById(id: String): Album {
        return RetrofitInstance.api.getAlbumById(id)
    }
}