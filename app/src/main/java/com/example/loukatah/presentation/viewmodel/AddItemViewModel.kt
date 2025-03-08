package com.example.loukatah.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loukatah.data.model.Item
import com.example.loukatah.domain.usecase.AddItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.Date
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val addItemUseCase: AddItemUseCase
) : ViewModel() {

    // Define the UI state as StateFlow
    private val _uiState = MutableStateFlow(AddItemUiState())
    val uiState: StateFlow<AddItemUiState> get() = _uiState

    // Handle events such as title, description changes, etc.
    fun onEvent(event: AddItemEvent) {
        when (event) {
            is AddItemEvent.TitleChange -> {
                _uiState.value = _uiState.value.copy(title = event.title)
            }
            is AddItemEvent.DescriptionChange -> {
                _uiState.value = _uiState.value.copy(description = event.description)
            }
            is AddItemEvent.StatusChange -> {
                _uiState.value = _uiState.value.copy(status = event.status)
            }
            is AddItemEvent.PictureChange -> {
                _uiState.value = _uiState.value.copy(picture = event.picture)
            }
            is AddItemEvent.CategoryChange -> {
                _uiState.value = _uiState.value.copy(category = event.category)
            }
            is AddItemEvent.SaveItem -> {
                saveItem()
            }
        }
    }

    // Save the item using the use case
    private fun saveItem() {
        viewModelScope.launch {
            val item = Item(
                id = "", // Ideally, this will be generated when saving (e.g., UUID)
                title = _uiState.value.title,
                description = _uiState.value.description,
                status = _uiState.value.status,
                picture = _uiState.value.picture,
                item_category = _uiState.value.category,
                coordinates = Pair(0.0, 0.0), // You can update with actual coordinates if needed
                date_lost = Date(), // Example timestamp
                createdAt = Date().toString(),
                updatedAt = Date()
            )
            addItemUseCase.invoke(item) // Call the use case to add the item
        }
    }
}
data class AddItemUiState(
    val title: String = "",
    val description: String = "",
    val status: String = "",
    val picture: String = "",
    val category: String = ""

)

sealed class AddItemEvent {
    data class TitleChange(val title: String): AddItemEvent()
    data class DescriptionChange(val description: String): AddItemEvent()
    data class StatusChange(val status: String): AddItemEvent()
    data class PictureChange(val picture: String): AddItemEvent()
    data class CategoryChange(val category: String): AddItemEvent()
    object SaveItem: AddItemEvent()
}