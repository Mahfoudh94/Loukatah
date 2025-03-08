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
    // Get current UI state
    val uiState by addItemViewModel.uiState.collectAsState()

    // Get category data from the ItemCategoryViewModel
    val categoryState by itemCategoryViewModel.categoryState.collectAsState()
    val categories = categoryState.categories

    // States for dropdown expansions
    var isStatusDropdownExpanded by remember { mutableStateOf(false) }
    var isCategoryDropdownExpanded by remember { mutableStateOf(false) }

    // Selected status and category
    var selectedStatus by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

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
                // Title Field
                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = { addItemViewModel.onEvent(AddItemEvent.TitleChange(it)) },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Description Field
                OutlinedTextField(
                    value = uiState.description,
                    onValueChange = { addItemViewModel.onEvent(AddItemEvent.DescriptionChange(it)) },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Status Dropdown
                ExposedDropdownMenuBox(
                    expanded = isStatusDropdownExpanded,
                    onExpandedChange = { isStatusDropdownExpanded = !isStatusDropdownExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedStatus,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Status") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isStatusDropdownExpanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isStatusDropdownExpanded,
                        onDismissRequest = { isStatusDropdownExpanded = false },
                    ) {
                        listOf("Lost", "Found").forEach { option ->
                            DropdownMenuItem(
                                text = { Text(text = option) },
                                onClick = {
                                    selectedStatus = option
                                    addItemViewModel.onEvent(AddItemEvent.StatusChange(option))
                                    isStatusDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Category Dropdown
                ExposedDropdownMenuBox(
                    expanded = isCategoryDropdownExpanded,
                    onExpandedChange = { isCategoryDropdownExpanded = !isCategoryDropdownExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedCategory.ifEmpty { "Select Category" },
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoryDropdownExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isCategoryDropdownExpanded,
                        onDismissRequest = { isCategoryDropdownExpanded = false },
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(text = category.name) },
                                onClick = {
                                    selectedCategory = category.name
                                    addItemViewModel.onEvent(AddItemEvent.CategoryChange(category.name))
                                    isCategoryDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Submit Button
                Button(
                    onClick = {
                        addItemViewModel.onEvent(AddItemEvent.SaveItem)
                        onItemAdded()  // callback after the item is added
                    },
                    enabled = uiState.title.isNotBlank() && uiState.description.isNotBlank() && selectedStatus.isNotBlank() && selectedCategory.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Add Item")
                }
            }
        }
    }
}
