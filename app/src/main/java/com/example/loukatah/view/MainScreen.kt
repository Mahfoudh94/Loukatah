package com.example.loukatah.view

import AddItemBottomSheet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.loukatah.viewmodel.ItemCategoryViewModel
import com.example.loukatah.viewmodel.ItemViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import com.example.loukatah.model.Item
import com.example.loukatah.model.ItemCategory
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    itemViewModel: ItemViewModel,
    itemCategoryViewModel: ItemCategoryViewModel,
    modifier: Modifier = Modifier,
) {
    val itemState by itemViewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showFilterDialog by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf<String?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SearchAppBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = {
                    searchQuery = it
                    itemViewModel.searchItems(it) // Search with filtering
                },
                onFilterClicked = { showFilterDialog = true }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showBottomSheet = true },
                containerColor = Color(0xFF03A9F4),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Item",
                    tint = Color.White
                )
            }
        },
        bottomBar = { BottomNavigationBar() } // Add Bottom Navigation
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                when {
                    itemState.isLoading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    itemState.items.isEmpty() -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .fillParentMaxHeight()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    AsyncImage(
                                        model = "https://app.lssquare.com/static/media/empty_product_banner.c076afe7.png",
                                        contentDescription = "No items found",
                                        modifier = Modifier.size(250.dp) // Adjust size as needed
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "No items found",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }

                    else -> {
                        items(itemState.items) { item ->
                            CardItem(item = item)
                        }
                    }
                }
            }
        }
    }

    // Show the filter dialog with selected status
    if (showFilterDialog) {
        FilterDialog(
            selectedStatus = selectedStatus,
            onDismiss = { showFilterDialog = false },
            onFilterSelected = { status ->
                selectedStatus = status
                itemViewModel.filterByStatus(status) // Apply filter when selected
            }
        )
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false }
        ) {
            AddItemBottomSheet(
                onItemAdded = { newItem ->
                    // Handle adding new item (e.g., save to ViewModel)
                    itemViewModel.addItem(newItem)
                    showBottomSheet = false
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onFilterClicked: () -> Unit
) {
    Surface( // Ensures proper background and padding
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, bottom = 10.dp, start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                modifier = Modifier
                    .weight(1f) // Makes TextField take available space
                    .height(56.dp),
                placeholder = { Text("Search items...") },
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = onFilterClicked) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Filter",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun FilterDialog(
    selectedStatus: String?, // Keep track of selected filter
    onDismiss: () -> Unit,
    onFilterSelected: (String?) -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Filter by Status") },
        text = {
            Column {
                FilterOption(
                    text = "All Items",
                    isSelected = selectedStatus == null,
                    onClick = { onFilterSelected(null); onDismiss() }
                )
                FilterOption(
                    text = "Lost Items",
                    isSelected = selectedStatus == "Lost",
                    onClick = { onFilterSelected("Lost"); onDismiss() }
                )
                FilterOption(
                    text = "Found Items",
                    isSelected = selectedStatus == "Found",
                    onClick = { onFilterSelected("Found"); onDismiss() }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Close")
            }
        }
    )
}

@Composable
fun FilterOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(
                if (isSelected) Color(0xFFE3F2FD) else Color.Transparent, // Light blue when selected
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            text = text,
            color = if (isSelected) Color(0xFF1976D2) else Color.Black, // Darker text when selected
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}


@Composable
fun CategoryRow(categories: List<ItemCategory>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(categories) { category ->
            CategoryItem(category = category)
        }
    }
}

@Composable
fun CategoryItem(category: ItemCategory) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = category.icon,
            contentDescription = category.name,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = category.name)
    }
}

@Composable
fun CardItem(item: Item) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(5.dp) // Rounded corners
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Item Image (If Available)
            item.picture?.let { imageUri ->
                AsyncImage(
                    model = imageUri,
                    contentDescription = item.title,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Item Details
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    // Status Badge
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (item.status == "Lost") Color(0xFFFFEAEA) else Color(
                                0xFFEAF8EA
                            )
                        ),
                        modifier = Modifier
                            .wrapContentSize(),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(
                            text = item.status, // Converts Enum to String
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = if (item.status == "Lost") Color(0xFFD32F2F) else Color(
                                0xFF388E3C
                            ),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Location Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Lat: ${item.coordinates.first}, Lng: ${item.coordinates.second}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }


        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = true, // Handle selection state properly
            onClick = { /* TODO: Navigate to Home */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.LocationOn, contentDescription = "Map") },
            label = { Text("Map") },
            selected = false,
            onClick = { /* TODO: Navigate to Map */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = false,
            onClick = { /* TODO: Navigate to Profile */ }
        )
    }
}