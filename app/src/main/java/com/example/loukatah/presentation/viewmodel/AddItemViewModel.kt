package com.example.loukatah.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loukatah.data.model.Item
import com.example.loukatah.domain.usecase.AddItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class AddItemViewModel @Inject constructor(private val addItemUseCase: AddItemUseCase): ViewModel() {

    //TODO: Add Item ViewModel logic
    private val _uiState = MutableStateFlow(AddItemUiState())
    val uiState: StateFlow<AddItemUiState> get() = _uiState

    fun onEvent(event: AddItemEvent) {
        when (event) {
            is AddItemEvent.TitleChange -> _uiState.value = _uiState.value.copy(title = event.title)
            is AddItemEvent.DescriptionChange -> _uiState.value =
                _uiState.value.copy(description = event.description)

            is AddItemEvent.StatusChange -> _uiState.value =
                _uiState.value.copy(status = event.status)

            is AddItemEvent.PictureChange -> _uiState.value =
                _uiState.value.copy(picture = event.picture)

            is AddItemEvent.CategoryChange -> _uiState.value =
                _uiState.value.copy(category = event.category)

            is AddItemEvent.SaveItem -> saveItem()
        }
    }

    private fun saveItem() {

        val state = _uiState.value
        if (state.title.isBlank() || state.description.isBlank() || state.status.isBlank() || state.category.isBlank()) {
            _uiState.value = state.copy(error = "يجب ملء جميع الحقول")
            println("DEBUG: هناك حقول فارغة")
            return
        }
        viewModelScope.launch {
            val item = Item(
                id = "", // Ideally, this will be generated when saving (e.g., UUID)
                title = state.title,
                description = state.description,
                status = state.status,
                picture = state.picture,
                item_category = state.category,
                coordinates = Pair(0.0, 0.0), // You can update with actual coordinates if needed
                date_lost = Date(), // Example timestamp
                createdAt = Date().toString(),
                updatedAt = Date()

            )

            _uiState.value = state.copy(isLoading = true)
            try {
                addItemUseCase.invoke(item)
                _uiState.value = state.copy(
                    isLoading = false,
                    isSuccess = true,
                    error = null
                )
                println("DEBUG: تمت إضافة العنصر بنجاح")
            } catch (e: Exception) {
                _uiState.value = state.copy(
                    isLoading = false,
                    isSuccess = false,
                    error = e.localizedMessage ?: "حدث خطأ أثناء الإضافة"
                )
                println("DEBUG: فشل إضافة العنصر - ${e.localizedMessage}")
                addItemUseCase.invoke(item) // Call the use case to add the item
            }
        }
    }


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

    sealed class AddItemEvent {
        data class TitleChange(val title: String) : AddItemEvent()
        data class DescriptionChange(val description: String) : AddItemEvent()
        data class StatusChange(val status: String) : AddItemEvent()
        data class PictureChange(val picture: String) : AddItemEvent()
        data class CategoryChange(val category: String) : AddItemEvent()
        object SaveItem : AddItemEvent()
    }
}
