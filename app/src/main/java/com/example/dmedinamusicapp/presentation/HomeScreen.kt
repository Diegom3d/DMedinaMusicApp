package com.example.dmedinamusicapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.dmedinamusicapp.data.Album
import com.example.dmedinamusicapp.navigation.Screen
import com.example.dmedinamusicapp.viewmodel.AlbumViewModel


@Composable
fun HomeScreen(navController: NavController, viewModel: AlbumViewModel) {
    val albums by viewModel.albums.collectAsState()
    val firstAlbum: Album? = albums.firstOrNull()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF6A1B9A), Color.White)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp) // espacio para el MiniPlayer
        ) {
            GreetingHeader(name = "Diego Medina")
            Spacer(modifier = Modifier.height(24.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Albums",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Text(
                        text = "See more",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                LazyRow(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(albums) { album ->
                        AlbumCard(album = album) {
                            navController.navigate(Screen.Detail.createRoute(album.id))
                        }
                    }
                }

                Text(
                    text = "Recently Played",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 12.dp)
                ) {
                    items(albums.take(4)) { album ->
                        RecentlyPlayedItem(album = album) {
                            navController.navigate(Screen.Detail.createRoute(album.id))
                        }
                    }
                }
            }
        }

        if (firstAlbum != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                MiniPlayer(album = firstAlbum)
            }
        }
    }
}

@Composable
fun GreetingHeader(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Good Morning!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Text(
                text = name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.White
            )
        }
    }
}

@Composable
fun RecentlyPlayedItem(album: Album, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(album.image),
                contentDescription = album.title,
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = album.title, fontWeight = FontWeight.Medium)
                Text(text = album.artist, fontSize = 12.sp, color = Color.Gray)
            }
            IconButton(onClick = { /* menú */ }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
            }
        }
    }
}