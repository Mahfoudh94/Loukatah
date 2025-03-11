package com.example.loukatah.presentation.viewmodel

import androidx.compose.animation.fadeIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loukatah.data.model.Item
import com.example.loukatah.domain.usecase.AddItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class AddItemViewModel @Inject constructor(private val addItemUseCase: AddItemUseCase): ViewModel() {
    //TODO: Add Item ViewModel logic
    private val _uiState = MutableStateFlow(AddItemUiState())
    val uiState : StateFlow<AddItemUiState> = _uiState.asStateFlow()

    fun onEvent(event: AddItemEvent){
        when(event) {
            is AddItemEvent.TitleChange -> {
                _uiState.update { it.copy(title = event.title) }
                val isValid = validate(_uiState.value)
                updateIsEnable(isValid);
            }
            is AddItemEvent.DescriptionChange -> {
                _uiState.update { it.copy(description = event.description) }
                val isValid = validate(_uiState.value)
                updateIsEnable(isValid);
            }
            is AddItemEvent.StatusChange -> {
                _uiState.update { it.copy(status = event.status) }
            }
            is AddItemEvent.PictureChange -> {
                _uiState.update { it.copy(picture = event.picture) }
                val isValid = validate(_uiState.value)
                updateIsEnable(isValid);
            }
            is AddItemEvent.CategoryChange -> {
                _uiState.update { it.copy(category = event.category) }
                val isValid = validate(_uiState.value)
                updateIsEnable(isValid);
            }
            is AddItemEvent.SaveItem -> {
                saveItem();
            }
        }
    }
    fun saveItem() {
        val currentState = _uiState.value
        viewModelScope.launch {
            addItemUseCase.invoke(Item(
                    id = "9",
                    title = currentState.title,
                    description = currentState.description,
                    status = currentState.status,
                    item_category = currentState.category,
                    coordinates = Pair(0.0, 0.0),
                    date_lost = Date(),
                    createdAt = "",
                    updatedAt = Date(),
                    picture = currentState.picture,
                ))
        }
    }
    fun validate(state: AddItemUiState): Boolean {
        val isTitleAndDescriptionAndCategoryValid = isValidTitle(state.title) && isValidDescription(state.description) && isValidCategory(state.category)
        val isPictureValid = isValidUrl(state.picture)
        return isTitleAndDescriptionAndCategoryValid && isPictureValid

    }
    fun isValidTitle(title: String) : Boolean{
        if(title.isNotBlank()){
            _uiState.update { it.copy(inValidTitle = false) }
            return true
        }else{
            _uiState.update { it.copy(inValidTitle = true) }
            return  false
        }
    }
    fun isValidDescription(description: String) : Boolean{
        if(description.isNotBlank()){
            _uiState.update { it.copy(inValidDesc = false) }
            return true
        }else{
            _uiState.update { it.copy(inValidDesc = true) }
            return  false
        }
    }

    fun isValidUrl(url: String): Boolean {
        // Regular expression to match a URL
        val urlRegex = """^(https?://)?([\w.-]+)(\.[a-zA-Z]{2,})(:[0-9]{1,5})?(/.*)?${'$'}""".toRegex()
        if(url.isNotBlank() && urlRegex.matches(url)){
            _uiState.update { it.copy(inValidPicureUrl = false) }
            return true
        }else{
            _uiState.update { it.copy(inValidPicureUrl = true) }
            return  false
        }
    }
    fun isValidCategory(category: String) : Boolean{
        if(category.isNotBlank()){
            _uiState.update { it.copy(inValidCategory = false) }
            return true
        }else{
            _uiState.update { it.copy(inValidCategory = true) }
            return  false
        }
    }

    fun updateIsEnable(isValid : Boolean){
        if(isValid){
            _uiState.update { it.copy(isEnable = true) }
        }else{
            _uiState.update { it.copy(isEnable = false) }
        }
    }

}

data class AddItemUiState(
    val title: String = "",
    val description: String = "",
    val status: String = "",
    val picture: String = "",
    val category: String = "",
    val isEnable: Boolean = false,
    val isLoading: Boolean = false,
    val inValidTitle : Boolean = true,
    val inValidDesc : Boolean = true,
    val inValidPicureUrl : Boolean = true,
    val inValidCategory: Boolean = true
)

sealed class AddItemEvent {
    data class TitleChange(val title: String): AddItemEvent()
    data class DescriptionChange(val description: String): AddItemEvent()
    data class StatusChange(val status: String): AddItemEvent()
    data class PictureChange(val picture: String): AddItemEvent()
    data class CategoryChange(val category: String): AddItemEvent()
    object SaveItem: AddItemEvent()
}