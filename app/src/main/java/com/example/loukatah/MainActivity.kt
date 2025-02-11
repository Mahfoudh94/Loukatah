package com.example.loukatah

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstUI(modifier = Modifier
                .padding(vertical = 20.dp))
        }
    }
}
/**
 * Main composable function for the UI layout
 * @param modifier Modifier for layout adjustments
 */
@SuppressLint("MutableCollectionMutableState")
@Composable
fun FirstUI(modifier: Modifier = Modifier) {
    // TODO 1: Create state variables for text input and items list
    var textInput by remember  { mutableStateOf("") }
    val inputs = remember      { mutableStateListOf<String>() }
    var searchList by remember { mutableStateOf(inputs.toList()) }
    Column(
        modifier = modifier
            .padding(25.dp)
            .fillMaxSize()
    ) {
        SearchInputBar(
            textValue = textInput,                  // TODO 2: Connect to state
            onTextValueChange = { text : String ->
                textInput = text    /* TODO 3: Update text state */
                if(textInput.isNotBlank()) {
                    searchList = inputs.filter { it.contains(text, ignoreCase = true)}
                    if (searchList.any { it.contains(text , ignoreCase = true) }){

                    }else{
                        searchList = emptyList()
                        searchList += "Input cannot be found"
                    }
                }else{
                    if(inputs.isNotEmpty()){
                        searchList = inputs
                    }
                }},
            onAddItem = { if(textInput.isNotBlank()){
                inputs.add(textInput)
                searchList = inputs
                textInput = ""
            }                                   /* TODO 4: Add item to list */ },
            onSearch = {
                    item ->
                if(textInput.isNotBlank()) {
                    searchList =  inputs.filter { it.contains(item, ignoreCase = true)}
                    textInput = ""
                }else{
                    searchList = inputs.toList()
                }
                /* TODO 5: Implement search functionality */ }
        )
        // TODO 6: Display list of items using CardsList composable
        CardsList( onClear = {
                text : String ->
            for (i in inputs){
                if ( i == text )
                    inputs.remove(i)
            }
        } ,
            searchList.toMutableList())
    }
}
/**
 * Composable for search and input controls
 * @param textValue Current value of the input field
 * @param onTextValueChange Callback for text changes
 * @param onAddItem Callback for adding new items
 * @param onSearch Callback for performing search
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
            Button(onClick = { onAddItem(textValue)
                /* TODO 7: Handle add button click */ }) {
                Text("Add")
            }
        }
    }
}
/**
 * Composable for displaying a list of items in cards
 * @param displayedItems List of items to display
 */
@Composable
fun CardsList( onClear: (String) -> Unit ,
               displayedItems: MutableList<String>)
{
    // TODO 9: Implement LazyColumn to display items
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        // TODO 10: Create cards for each item in the list
        items(displayedItems) { item ->
            if(item != "Input cannot be found")
            {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(text = item, modifier = Modifier
                            .padding(16.dp)
                            .weight(4f)
                        )
                        IconButton(
                            modifier = Modifier
                                .weight(1f),
                            onClick = {
                                onClear(item)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "clear icon"
                            )
                        }

                    }

                }
            }else{
                Box {
                    Text(text = item, modifier = Modifier
                        .padding(60.dp),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}