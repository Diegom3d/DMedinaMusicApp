package com.example.dmedinamusicapp.data

import java.io.Serializable
import com.google.gson.annotations.SerializedName


data class Album(
    @SerializedName(value = "id", alternate = ["_id"])
    val id: String,
    val title: String,
    val artist: String,
    val description: String,
    val image: String,
    val tracks: List<String> = emptyList()
) : Serializable