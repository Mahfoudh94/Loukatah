package com.example.loukatah.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loukatah.domain.usecase.AddItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.loukatah.data.model.Item

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val addItemUseCase: AddItemUseCase
) : ViewModel() {

    // State management using StateFlow
    private val _uiState = MutableStateFlow(AddItemUiState())
    val uiState: StateFlow<AddItemUiState> = _uiState.asStateFlow()

    // Handle events from the UI
    fun onEvent(event: AddItemEvent) {
        when (event) {
            is AddItemEvent.TitleChange -> {
                _uiState.update { it.copy(title = event.title) }
            }
            is AddItemEvent.DescriptionChange -> {
                _uiState.update { it.copy(description = event.description) }
            }
            is AddItemEvent.StatusChange -> {
                _uiState.update { it.copy(status = event.status) }
            }
            is AddItemEvent.PictureChange -> {
                _uiState.update { it.copy(picture = event.picture) }
            }
            is AddItemEvent.CategoryChange -> {
                _uiState.update { it.copy(category = event.category) }
            }
            AddItemEvent.SaveItem -> {
                saveItem()
            }
        }
    }

    // Save item to the repository
    private fun saveItem() {
        if (!validateInputs()) {
            _uiState.update { it.copy(error = "Please fill all fields") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val item = Item(
                    title = _uiState.value.title,
                    description = _uiState.value.description,
                    status = _uiState.value.status,
                    picture = _uiState.value.picture,
                    item_category = _uiState.value.category,
                    id = TODO(),
                    coordinates = TODO(),
                    date_lost = TODO(),
                    createdAt = TODO(),
                    updatedAt = TODO()
                )
                addItemUseCase(item)
                _uiState.update { it.copy(isSuccess = true, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    // Validate input fields
    private fun validateInputs(): Boolean {
        return _uiState.value.title.isNotEmpty() &&
                _uiState.value.description.isNotEmpty() &&
                _uiState.value.status.isNotEmpty() &&
                _uiState.value.picture.isNotEmpty() &&
                _uiState.value.category.isNotEmpty()
    }
}

// UI State for AddItemScreen
data class AddItemUiState(
    val title: String = "",
    val description: String = "",
    val status: String = "",
    val picture: String = "",
    val category: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

// Events for AddItemScreen
sealed class AddItemEvent {
    data class TitleChange(val title: String) : AddItemEvent()
    data class DescriptionChange(val description: String) : AddItemEvent()
    data class StatusChange(val status: String) : AddItemEvent()
    data class PictureChange(val picture: String) : AddItemEvent()
    data class CategoryChange(val category: String) : AddItemEvent()
    object SaveItem : AddItemEvent()
}