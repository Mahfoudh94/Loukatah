package com.example.loukatah.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.Alignment
import com.example.loukatah.model.Item
import com.example.loukatah.model.ItemCategory
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.loukatah.R

@Composable
fun MainScreen(
    itemViewModel: ItemViewModel,
    itemCategoryViewModel: ItemCategoryViewModel,
    modifier: Modifier = Modifier,
    onSelectItem: (Item) -> Unit
) {
    Scaffold(){
        padding ->
        val itemState by itemViewModel.uiState.collectAsState()
        val categoryState by itemCategoryViewModel.categoryState.collectAsState()
        var showCategoryList = remember { mutableStateOf(false) }
        var searchQuery = remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .padding(start = 20.dp,end = 20.dp, bottom = 20.dp,top = 30.dp)
                .fillMaxSize()
        ) {
        SearchBar(
            textValue = searchQuery.value,
            showCategoryList = showCategoryList.value,
            onFilter = {
                showCategoryList.value = it
            },
            onSearch = {
                searchQuery.value = it
                itemViewModel.getItems(searchQuery.value)
            }
        )
            Spacer(modifier = Modifier.height(8.dp))
            if (showCategoryList.value) {
                ItemCategoryList(categories = categoryState.categories)
            }
        LazyColumn {
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
                        ItemCard(
                            item = item,
                            onSelect = {
                                onSelectItem(item)
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
        }
    }
}
//@Preview
@Composable
fun SearchBar(
    textValue: String,
    showCategoryList : Boolean = false,
    onFilter : (Boolean) -> Unit,
    onSearch : (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = textValue,
            onValueChange = {onSearch(it)},
            label = { Text(text = "Search items...") },
            modifier = Modifier.weight(1f)
                .background( color = Color(0xFFF8F9FA),shape = RoundedCornerShape(20.dp))
            ,
        )
        IconButton(onClick = { onFilter(!showCategoryList)}) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.filter_icon),
                contentDescription = "Filter",
            )
        }
    }
}

@Composable
fun ItemCard(
    item : Item,
    onSelect : () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
        ,
        shape = RoundedCornerShape(12.dp),
        onClick = {onSelect()}
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = item.picture,
                contentDescription = item.description,
                modifier = Modifier.size(100.dp)
                    .fillMaxHeight()
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxSize()){
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    StatusTag(status = item.status)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color.Gray
                    )
                    Text(text = item.location, fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
}
@Composable
fun StatusTag(status: String) {
    Box(
        modifier = Modifier
            .background(
                color = if (status == "Lost") Color.Red else Color.Green,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = status, color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun ItemCategoryList(categories: List<ItemCategory>){
    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        items(categories) { category ->
            CategoryItem(category = category)
        }
    }
}
@Composable
fun CategoryItem(category: ItemCategory){
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = category.icon,
            contentDescription = category.name,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = category.name)
    }
}