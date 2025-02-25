package com.example.loukatah.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.loukatah.model.Item
import com.example.loukatah.viewmodel.ItemCategoryViewModel
import com.example.loukatah.viewmodel.ItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(itemViewModel: ItemViewModel, itemCategoryViewModel: ItemCategoryViewModel, modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedItem by remember { mutableStateOf(0) } // لتتبع العنصر المحدد في شريط التنقل

    Scaffold(
        topBar = {
            // شريط البحث في الجزء العلوي
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { /* تنفيذ البحث */ },
                active = false,
                onActiveChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = SearchBarDefaults.colors(containerColor = Color.LightGray),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "بحث", tint = Color.Gray)
                }
            ) {}
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* عمل شيء عند النقر على زر الزائد */ },
                containerColor = Color.Blue, // لون الزر
                contentColor = Color.White // لون الأيقونة
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        bottomBar = {
            MyBottomNavigation(selectedItem = selectedItem, onItemSelected = { selectedItem = it })
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            val itemState by itemViewModel.uiState.collectAsState()
            val categoryState by itemCategoryViewModel.categoryState.collectAsState()

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                when {
                    itemState.isLoading -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize().padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    itemState.items.isEmpty() -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize().padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "No items found", fontSize = 16.sp)
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
}
@Composable
fun CardItem(item: Item) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // عرض الصورة على اليسار
            AsyncImage(
                model = item.picture,
                contentDescription = "Item Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // عرض النص في الوسط
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title ?: "No Name",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.description ?: "No Description",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = Color.Red,
                        modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = item.location ?: "No Location",
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                }
            }

            // زر Lost أو Found على اليمين
            Button(
                onClick = { /* عمل شيء عند النقر */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (item.status == "Lost") Color.Red else Color.Green
                ),
                modifier = Modifier
                    .width(80.dp)
                    .height(40.dp)
            ) {
                Text(
                    text = item.status ?: "Unknown",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun MyBottomNavigation(selectedItem: Int, onItemSelected: (Int) -> Unit) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color.White, // لون خلفية الشريط
        contentColor = Color.Black // لون العناصر
    ) {
        // أيقونة المنزل
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            },
            label = {
                Text(
                    text = "Home",
                    fontSize = 12.sp
                )
            },
            selected = selectedItem == 0,
            onClick = { onItemSelected(0) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Blue, // لون الأيقونة المحددة
                selectedTextColor = Color.Blue, // لون النص المحدد
                unselectedIconColor = Color.Gray, // لون الأيقونة غير المحددة
                unselectedTextColor = Color.Gray // لون النص غير المحدد
            )
        )

        // أيقونة المستخدم
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,contentDescription = "Profile"
                )
            },
            label = {
                Text(
                    text = "Profile",
                    fontSize = 12.sp
                )
            },
            selected = selectedItem == 1,
            onClick = { onItemSelected(1) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Blue,
                selectedTextColor = Color.Blue,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )

        // أيقونة الإعدادات
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            },
            label = {
                Text(
                    text = "Settings",
                    fontSize = 12.sp
                )
            },
            selected = selectedItem == 2,
            onClick = { onItemSelected(2) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Blue,
                selectedTextColor = Color.Blue,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )