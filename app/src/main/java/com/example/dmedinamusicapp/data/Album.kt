package com.example.dmedinamusicapp.data

import java.io.Serializable

data class Album(
    val id: String,
    val title: String,
    val artist: String,
    val description: String,
    val image: String,
    val tracks: List<String>
) : Serializable