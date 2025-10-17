package com.example.dmedinamusicapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.dmedinamusicapp.data.Album
import com.example.dmedinamusicapp.viewmodel.AlbumViewModel

@Composable
fun DetailScreen(viewModel: AlbumViewModel) {
    val album by viewModel.selectedAlbum.collectAsState()

    if (album == null) return

    Column(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo con scrim
        Box(modifier = Modifier.height(300.dp)) {
            Image(
                painter = rememberAsyncImagePainter(album!!.image),
                contentDescription = album!!.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xCC4A148C)),
                            startY = 100f
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = album!!.title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp
                )
                Text(
                    text = album!!.artist,
                    fontSize = 16.sp,
                    color = Color.LightGray
                )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ActionButton(icon = Icons.Default.PlayArrow, label = "Play")
                ActionButton(icon = Icons.Default.Refresh, label = "Replay")
            }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White.copy(alpha = 0.05f), shape = MaterialTheme.shapes.medium)
                .padding(16.dp)
        ) {
            Column {
                Text("About this album", fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = album!!.description, color = Color.Black)
            }
        }


        AssistChip(
            onClick = {  },
            label = { Text("Artist: ${album!!.artist}") },
            modifier = Modifier.padding(horizontal = 18.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            items((1..6).map { "Track $it" }) { track ->
                TrackItem(
                    title = "${album!!.title} • $track",
                    artist = album!!.artist,
                    image = album!!.image,
                    duration = "4:${(10..59).random()}"
                )
                Divider(color = Color(0x33FFFFFF))
            }
        }

        // MiniPlayer inferior
        MiniPlayer(album = album!!)
    }
}

@Composable
fun ActionButton(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = label, tint = Color.White)
        Text(label, color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun TrackItem(title: String, artist: String, image: String, duration: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = title,
            modifier = Modifier
                .size(48.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.Medium, color = Color.Black)
            Text(text = artist, fontSize = 12.sp, color = Color.Gray)
        }
        Text(text = duration, fontSize = 12.sp, color = Color.Black)
        IconButton(onClick = {  }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = Color.White)
        }
    }
}