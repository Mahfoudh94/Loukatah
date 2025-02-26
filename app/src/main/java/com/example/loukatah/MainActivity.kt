package com.example.loukatah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.loukatah.ui.theme.LoukatahTheme

val Purple500 = Color(0xFF6200EE)  // Custom Purple color
val Teal200 = Color(0xFF03DAC6)
val BackgroundColor = Color(0xFFF5F5F5)
val TextColor = Color(0xFF212121)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoukatahTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FirstUI(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun FirstUI(modifier: Modifier = Modifier) {
    var textValue by remember { mutableStateOf("") }
    var allItems = remember { mutableStateListOf("ahmed", "hocine", "Adel") }
    var displayedItems by remember { mutableStateOf(allItems.toList()) }

    Column(
        modifier = modifier
            .padding(25.dp)
            .fillMaxSize()
    ) {
        SearchInputBar(
            textValue = textValue,
            onTextValueChange = { value ->
                textValue = value
                displayedItems = if (textValue.isNotEmpty()) {
                    allItems.filter { it.contains(value, ignoreCase = true) }
                } else {
                    allItems.toList()
                }
            },
            onAddItem = { itemToAdd ->
                if (textValue.isNotEmpty()) {
                    allItems.add(itemToAdd)
                    displayedItems = allItems.toList()
                    textValue = ""
                }
            },
            onSearch = { itemToSearch ->
                if (textValue.isNotEmpty()) {
                    displayedItems = allItems.filter { it.contains(itemToSearch, ignoreCase = true) }
                    textValue = ""
                } else {
                    displayedItems = allItems.toList()
                }
            }
        )

        CardsList(
            displayedItems = displayedItems,
            onRemove = { itemToRemove ->
                allItems.remove(itemToRemove)
                displayedItems = allItems.toList()
            }
        )
    }
}

@Composable
fun SearchInputBar(
    textValue: String,
    onTextValueChange: (String) -> Unit,
    onAddItem: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = textValue,
            onValueChange = onTextValueChange,
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = { onAddItem(textValue) },
            enabled = textValue.isNotEmpty() // Disable when input is empty
        ) {
            Text("Add")
        }
        Button(onClick = { onSearch(textValue) }) {
            Text("Search")
        }
    }
}

@Composable
fun CardsList(
    displayedItems: List<String>,
    onRemove: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        if (displayedItems.isNotEmpty()) {
            items(displayedItems) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = item)
                        IconButton(onClick = { onRemove(item) }) {
                            // Set the delete icon color to Purple500
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Delete",
                                tint = Purple500  // Setting the color for the icon
                            )
                        }
                    }
                }
            }
        } else {
            item {
                Text(
                    text = if (displayedItems.isEmpty()) "No results found" else "No items to display",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

