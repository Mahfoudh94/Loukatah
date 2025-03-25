package com.example.loukatah.presentation.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.loukatah.data.model.Item
import com.example.loukatah.presentation.view.components.StatusTag
import com.example.loukatah.presentation.viewmodel.ItemViewModel
import java.text.SimpleDateFormat

/**
 * Screen for displaying detailed information about a specific item
 * 
 * @param itemId ID of the item to display
 * @param onBackClick Callback when the back button is clicked
 * @param modifier Modifier for customizing the layout
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    itemId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    itemViewModel: ItemViewModel = hiltViewModel()
) {
    val uiState by itemViewModel.uiState.collectAsState()
    var item by remember { mutableStateOf<Item?>(null) }
    
    // Find the item with the matching ID
    LaunchedEffect(itemId, uiState.items) {
        item = uiState.items.find { it.id == itemId }
        if (item == null && !uiState.isLoading) {
            // If item is not found and not currently loading, fetch items
            itemViewModel.getItems()
        }
    }
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Item Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }
                item == null -> {
                    Text(text = "Item not found")
                }
                else -> {
                    ItemDetailContent(item = item!!)
                }
            }
        }
    }
}


@Composable
fun ItemDetailContent(
    item: Item,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Item image
        AsyncImage(
            model = item.picture,
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Item title and status
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f)
            )
            StatusTag(status = item.status)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Category
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Category:",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = item.item_category,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        
        // Description
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        
        // Location
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Location",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Coordinates: ${item.coordinates.first}, ${item.coordinates.second}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        
        // Dates
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Dates",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(
                        text = "Lost/Found Date:",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = SimpleDateFormat("MMM dd, yyyy").format(item.date_lost),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = "Created:",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = item.createdAt,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = "Updated:",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = SimpleDateFormat("MMM dd, yyyy").format(item.updatedAt),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
