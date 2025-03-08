package com.example.loukatah.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loukatah.data.model.Item
import com.example.loukatah.domain.usecase.GetItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing item data
 */
@HiltViewModel
class ItemViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemUiState())
    val uiState: StateFlow<ItemUiState> = _uiState.asStateFlow()

    init {
        getItems()
    }

    fun getItems() {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(error = null, isLoading = true)


            getItemsUseCase().catch {

                _uiState.value = _uiState.value.copy(
                    error = "Failed to fetch items", isLoading = false, items = emptyList()
                )

            }.collect { items ->

                _uiState.value = _uiState.value.copy(items = items, isLoading = false, error = null)

            }
        }
    }
}

data class ItemUiState(
    val items: List<Item> = emptyList(), val isLoading: Boolean = false, val error: String? = null
)
