package com.example.loukatah.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.loukatah.domain.usecase.AddItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AddItemViewModel @Inject constructor(private val addItemUseCase: AddItemUseCase): ViewModel() {

    //TODO: Add Item ViewModel logic

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