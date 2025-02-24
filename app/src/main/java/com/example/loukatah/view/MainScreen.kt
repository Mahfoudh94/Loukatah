package com.example.loukatah.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.loukatah.model.Item
import com.example.loukatah.viewmodel.ItemViewModel
import java.text.SimpleDateFormat

@Composable
fun MainScreen(itemViewModel: ItemViewModel, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(16.dp))
        TopBar()
        val itemState by itemViewModel.uiState.collectAsState()

        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
            when {
                itemState.isLoading -> {
                    item {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }
                itemState.items.isEmpty() -> {
                    item {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "No items found")
                        }
                    }
                }
                else -> {
                    items(itemState.items) { item ->
                        CardItem(item)
                    }
                }
            }
        }
        FloatingActionButton(onClick = { /* Add new item */ }, modifier = Modifier.align(Alignment.End).padding(16.dp)) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
        BottomNavigationBar()
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            modifier = Modifier.weight(1f).padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = { /* Open categories */ }, modifier = Modifier.padding(top = 8.dp)) {
            Icon(Icons.Default.GridView, contentDescription = "Categories")
        }
    }
}

@Composable
fun CardItem(item: Item) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), shape = RoundedCornerShape(8.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = item.picture,
                contentDescription = item.title,
                modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.title, style = MaterialTheme.typography.titleLarge)
                Text(text = item.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Lost/Found Date: ${SimpleDateFormat("MMM dd, yyyy").format(item.date_lost)}")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (item.status == "Lost") Color.Red else Color.Green
                )
            ) {
                Text(text = item.status, color = Color.White, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Map, contentDescription = "Map") },
            label = { Text("Map") },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = false,
            onClick = {}
        )
    }
}
