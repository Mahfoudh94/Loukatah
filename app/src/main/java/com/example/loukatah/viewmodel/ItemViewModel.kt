package com.example.loukatah.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loukatah.model.Item
import com.example.loukatah.repository.ItemRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UIState(
    val isLoading: Boolean = false,
    val items: List<Item> = emptyList(),
    val filteredItems: List<Item> = emptyList(),
    val error: String? = null
)

class ItemViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private var allItems: List<Item> = emptyList() // Store all items
    private var searchQuery: String = "" // Track current search query
    private var selectedStatus: String? = null // Track selected status

    init {
        getItems()
    }

    fun getItems() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                delay(2000) // Simulate loading
                allItems = ItemRepository.getItems()
                applyFilters() // Apply filters after fetching data
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }

    fun searchItems(query: String) {
        searchQuery = query // Store search query
        applyFilters() // Apply both search and filter
    }

    fun filterByStatus(status: String?) {
        selectedStatus = status // Store selected filter
        applyFilters() // Apply both search and filter
    }

    private fun applyFilters() {
        val filteredItems = allItems.filter { item ->
            val matchesSearch = searchQuery.isBlank() || item.title.contains(searchQuery, ignoreCase = true)
            val matchesStatus = selectedStatus == null || item.status == selectedStatus
            matchesSearch && matchesStatus
        }

        _uiState.value = _uiState.value.copy(items = filteredItems, isLoading = false)
    }

    fun addItem(newItem: Item) {
        _uiState.value = _uiState.value.copy(
            items = _uiState.value.items + newItem
        )
    }
}

