package com.example.dmedinamusicapp.data

import retrofit2.http.GET
import retrofit2.http.Path

interface MusicApiService {
    @GET("albums")
    suspend fun getAlbums(): List<Album>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") id: String): Album
}