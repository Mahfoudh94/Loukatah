package com.example.loukatah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.loukatah.ui.theme.LoukatahTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoukatahTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FirstUI(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

/**
 * Main composable function for the UI layout
 */
@Composable
fun FirstUI(modifier: Modifier = Modifier) {
    // TODO 1: Create state variables for text input and items list
    var text by remember { mutableStateOf("") }
    val itemList = remember { mutableStateListOf<String>() }
    val displayedItems = remember { mutableStateListOf<String>() }

    Column(
        modifier = modifier
            .padding(25.dp)
            .fillMaxSize()
    ) {
        SearchInputBar(
            textValue = text, // TODO 2: Connect to state
            onTextValueChange = { text = it }, // TODO 3: Update text state
            onAddItem = {
                if (it.isNotBlank()) {
                    itemList.add(it) // إضافة العنصر إلى القائمة
                    displayedItems.add(it) // تحديث القائمة المعروضة
                    text = "" // مسح حقل الإدخال بعد الإضافة
                }
            },
            onSearch = { query ->
                displayedItems.clear()
                displayedItems.addAll(itemList.filter { it.contains(query, ignoreCase = true) }) // تصفية القائمة
            }
        )

        // TODO 6: Display list of items using CardsList composable
        CardsList(displayedItems)
    }
}

/**
 * Composable for search and input controls
 */
@Composable
fun SearchInputBar(
    textValue: String,
    onTextValueChange: (String) -> Unit,
    onAddItem: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    Column {
        TextField(
            value = textValue,
            onValueChange = onTextValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter text...") }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { onAddItem(textValue) }) { // TODO 7: Handle add button click
                Text("Add")
            }

            Button(onClick = { onSearch(textValue) }) { // TODO 8: Handle search button click
                Text("Search")
            }
        }
    }
}

/**
 * Composable for displaying a list of items in cards
 */
@Composable
fun CardsList(displayedItems: List<String>) {
    // TODO 9: Implement LazyColumn to display items
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        // TODO 10: Create cards for each item in the list
        items(displayedItems) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(text = item, modifier = Modifier.padding(16.dp)) // عرض العنصر الحقيقي
            }
        }
    }
}