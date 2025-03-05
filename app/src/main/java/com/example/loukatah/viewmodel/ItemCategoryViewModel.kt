package com.example.loukatah.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loukatah.model.ItemCategory
import com.example.loukatah.repository.ItemCategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CategoryState(
    val categories: List<ItemCategory> = emptyList()
)

class ItemCategoryViewModel() : ViewModel() {

    private val _categoryState = MutableStateFlow(CategoryState())
    val categoryState: StateFlow<CategoryState> = _categoryState.asStateFlow()


    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            val categories = ItemCategoryRepository.getCategories()
            _categoryState.value = _categoryState.value.copy(categories = categories)
        }
    }
}
