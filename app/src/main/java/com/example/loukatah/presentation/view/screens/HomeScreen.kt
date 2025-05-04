package com.example.loukatah.presentation.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.loukatah.data.model.Item
import com.example.loukatah.presentation.view.components.ItemCard
import com.example.loukatah.presentation.view.components.SearchBar
import com.example.loukatah.presentation.view.components.ShimmerItemList
import com.example.loukatah.presentation.viewmodel.ItemCategoryViewModel
import com.example.loukatah.presentation.viewmodel.ItemViewModel


@Composable
fun HomeScreen(
    onItemClick: (String) -> Unit,
    onAddItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    itemViewModel: ItemViewModel = hiltViewModel(),
    itemCategoryViewModel: ItemCategoryViewModel = hiltViewModel()
) {
    // State
    val itemState by itemViewModel.uiState.collectAsState()
   

    // Search query
    var searchQuery by remember { mutableStateOf("") }


    // Filter items by search query
    val filteredItems = itemState.items.filter { item ->
        item.description.contains(searchQuery, ignoreCase = true) ||
        item.title.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { newQuery ->
                    searchQuery = newQuery
                },
                onClearClick = {
                    searchQuery = ""
                },
                onMenuClick = { /* Menu action if needed */ }
            )
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(40.dp))
                    .background(Color.Black)
                    .clickable { onAddItemClick() }
                    .size(60.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Item",
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            when {
                itemState.isLoading -> {
                    item {
                        ShimmerItemList()
                    }
                }
                filteredItems.isEmpty() -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No items found")
                        }
                    }
                }
                else -> {
                    items(filteredItems) { item ->
                        ItemCard(
                            item = item,
                            onItemClick = onItemClick
                        )
                    }
                }
            }
        }
    }
}
