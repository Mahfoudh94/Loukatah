package com.example.loukatah.presentation.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.BeyondBoundsLayout
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.loukatah.presentation.viewmodel.AddItemEvent
import com.example.loukatah.presentation.viewmodel.AddItemViewModel
import com.example.loukatah.presentation.viewmodel.ItemCategoryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    onBackClick: () -> Unit,
    onItemAdded: () -> Unit,
    modifier: Modifier = Modifier,
    itemCategoryViewModel: ItemCategoryViewModel = hiltViewModel(),
    addItemViewModel: AddItemViewModel = hiltViewModel()
) {
    // State
    //--status
    var statusIsExpanded by remember { mutableStateOf(false) };
    var status by remember { mutableStateOf("Lost") }
    //--title
    var title by remember { mutableStateOf("") }
    //--description
    var description by remember { mutableStateOf("") }
    //--image url
    var imageUrl by remember { mutableStateOf("") }
    //--category
    var categoryIsExpanded by remember { mutableStateOf(false) };
    var categoryValue by remember { mutableStateOf("") }
    val categoryState by itemCategoryViewModel.categoryState.collectAsState()
    val categories = categoryState.categories
    //--uistate
    val uiState by addItemViewModel.uiState.collectAsState()
    // Events
    //--title
    addItemViewModel.onEvent(AddItemEvent.TitleChange(title))
    //--description
    addItemViewModel.onEvent(AddItemEvent.DescriptionChange(description))
    //--category
    addItemViewModel.onEvent(AddItemEvent.CategoryChange(categoryValue))
    //--status
    addItemViewModel.onEvent(AddItemEvent.StatusChange(status))
    //--image url
    addItemViewModel.onEvent(AddItemEvent.PictureChange(imageUrl))
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Add New Item") },
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
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Title
                OutlinedTextField(
                    value = title,//TODO() ,
                    onValueChange = { title = it},//TODO() ,
                    label = { Text("Title") },
                    textStyle = TextStyle(textAlign = TextAlign.Right),
                    modifier = Modifier.fillMaxWidth()
                )
                if(uiState.inValidTitle){
                    Text(text = "Title could not be blank " ,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Description
                OutlinedTextField(
                    value = description,//TODO() ,
                    onValueChange = { description = it},//TODO() ,
                    label = { Text("Description") },
                    textStyle = TextStyle(textAlign = TextAlign.Right),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
                if(uiState.inValidDesc){
                    Text(text = "Description could not be blank " ,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Status dropdown
                ExposedDropdownMenuBox(
                    expanded = statusIsExpanded ,//TODO
                    onExpandedChange = { statusIsExpanded = !statusIsExpanded }, //TODO
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = status, //TODO
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Status") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusIsExpanded, //TODO
                            ) },
                        textStyle = TextStyle(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    
                    ExposedDropdownMenu(
                        expanded = statusIsExpanded, //TODO
                        onDismissRequest = { statusIsExpanded = false} ,//TODO
                    ) {
                        listOf("Lost", "Found").forEach { option ->
                            DropdownMenuItem(
                                text = { Text(text = option) },
                                onClick = {
                                    status = option
                                    statusIsExpanded = !statusIsExpanded
                                }
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Category dropdown
                ExposedDropdownMenuBox(
                    expanded = categoryIsExpanded, //TODO
                    onExpandedChange = { categoryIsExpanded = !categoryIsExpanded}, //TODO
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value =  categoryValue,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryIsExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    
                    ExposedDropdownMenu(
                        expanded = categoryIsExpanded,//TODO
                        onDismissRequest = { categoryIsExpanded = false} , //TODO
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(text = category.name) },
                                onClick = {
                                    //TODO
                                    categoryValue = category.name
                                    categoryIsExpanded = false
                                }
                            )
                        }
                    }
                }
                if(uiState.inValidCategory){
                    Text(text = "Please choose category " ,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                //Image url
                OutlinedTextField(
                    value = imageUrl,//TODO() ,
                    onValueChange = {imageUrl = it },//TODO() ,
                    label = { Text("Image url") },
                    modifier = Modifier.fillMaxWidth()
                )
                if(uiState.inValidPicureUrl){
                    Text(text = "Picture url could only be a url form " ,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                
                // Submit button
                Button(
                    onClick = {
                        // In a real app, we would save the item here
                        // For now, just call the callback
                        addItemViewModel.onEvent(AddItemEvent.SaveItem)
                        onItemAdded()
                    },
                    enabled = uiState.isEnable, // TODO
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Add Item")
                }
                if(uiState.isLoading){
                    Text(text = "Adding item...")
                }            }
        }
    }
}
