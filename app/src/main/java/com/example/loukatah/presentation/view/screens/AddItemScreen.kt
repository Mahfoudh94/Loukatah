package com.example.loukatah.presentation.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
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
    val categoryState by itemCategoryViewModel.categoryState.collectAsState()
    val categories = categoryState.categories

    val uiState by addItemViewModel.uiState.collectAsState()
    var isImageError by remember { mutableStateOf(false) }

    // Events
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
                    value = uiState.title,
                    onValueChange = { addItemViewModel.onEvent(AddItemEvent.TitleChange(it)) },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Description
                OutlinedTextField(
                    value = uiState.description,
                    onValueChange = { addItemViewModel.onEvent(AddItemEvent.DescriptionChange(it)) },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Image URL
                OutlinedTextField(
                    value = uiState.picture,
                    onValueChange = {
                        addItemViewModel.onEvent(AddItemEvent.PictureChange(it))
                        isImageError = it.isBlank()
                    },
                    label = { Text("Image URL") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = isImageError
                )

                if (isImageError) {
                    Text(
                        text = "Image URL is required",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Status dropdown
                var statusExpanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = statusExpanded,
                    onExpandedChange = { statusExpanded = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = uiState.status,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Status") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = statusExpanded,
                        onDismissRequest = { statusExpanded = false },
                    ) {
                        listOf("Lost", "Found").forEach { option ->
                            DropdownMenuItem(
                                text = { Text(text = option) },
                                onClick = {
                                    addItemViewModel.onEvent(AddItemEvent.StatusChange(option))
                                    statusExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Category dropdown
                var categoryExpanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = { categoryExpanded = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = uiState.category,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false },
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(text = category.name) },
                                onClick = {
                                    addItemViewModel.onEvent(AddItemEvent.CategoryChange(category.name))
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Submit button
                Button(
                    onClick = {
                        if (uiState.picture.isBlank()) {
                            isImageError = true
                        } else {
                            addItemViewModel.onEvent(AddItemEvent.SaveItem)
                            if (uiState.isSuccess) {
                                onItemAdded()
                            }
                        }
                    },
                    enabled = !uiState.isLoading && uiState.picture.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Add Item")
                }

                // Loading Indicator
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                // Error Message
                uiState.error?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                // Success Message
                if (uiState.isSuccess) {
                    Text(
                        text = "Item added successfully",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}