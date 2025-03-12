package com.example.loukatah.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.loukatah.viewmodel.ItemCategoryViewModel
import com.example.loukatah.viewmodel.ItemViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import com.example.loukatah.model.Item
import com.example.loukatah.model.ItemCategory
import java.text.SimpleDateFormat
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MainScreen(itemViewModel: ItemViewModel, itemCategoryViewModel: ItemCategoryViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        val itemState by itemViewModel.uiState.collectAsState()
        val categoryState by itemCategoryViewModel.categoryState.collectAsState()
        CategoryRow(categoryState.categories)
        LazyColumn(modifier = modifier.fillMaxSize()) {
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
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No items found")
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

@Composable
fun CategoryRow(categories: List<ItemCategory>) {
    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
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
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = item.picture,
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Status: ",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = item.status,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = when (item.status) {
                            "Lost" -> Color.Red
                            "Found" -> Color.Green
                            else -> MaterialTheme.colorScheme.onSurface
                        }
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Category: ${item.item_category}",
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Lost/Found Date: ${SimpleDateFormat("MMM dd, yyyy").format(item.date_lost)}",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}