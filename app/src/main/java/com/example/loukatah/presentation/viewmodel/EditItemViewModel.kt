package com.example.loukatah.presentation.viewmodel

import androidx.compose.animation.fadeIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loukatah.data.model.Item
import com.example.loukatah.domain.usecase.EditItemUseCase
import com.google.firebase.auth.FirebaseAuth
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
class EditItemViewModel @Inject constructor(private val editItemUseCase: EditItemUseCase): ViewModel() {
    //TODO: Add Item ViewModel logic
    private val _uiState = MutableStateFlow(EditItemUiState())
    val uiState : StateFlow<EditItemUiState> = _uiState.asStateFlow()

    fun onEvent(event: EditItemEvent){
        when(event) {
            is EditItemEvent.TitleChange -> {
                _uiState.update { it.copy(title = event.title) }
                val isValid = validate(_uiState.value)
                updateIsEnable(isValid);
            }
            is EditItemEvent.DescriptionChange -> {
                _uiState.update { it.copy(description = event.description) }
                val isValid = validate(_uiState.value)
                updateIsEnable(isValid);
            }
            is EditItemEvent.StatusChange -> {
                _uiState.update { it.copy(status = event.status) }
            }
            is EditItemEvent.PictureChange -> {
                _uiState.update { it.copy(picture = event.picture) }
                val isValid = validate(_uiState.value)
                updateIsEnable(isValid);
            }
            is EditItemEvent.CategoryChange -> {
                _uiState.update { it.copy(category = event.category) }
                val isValid = validate(_uiState.value)
                updateIsEnable(isValid);
            }
            is EditItemEvent.SaveItem -> {
                saveItem(
                    idDoc = event.idDoc
                );
            }
        }
    }
    fun saveItem(idDoc: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val currentState = _uiState.value
        viewModelScope.launch {
            editItemUseCase.invoke(Item(
                    id = Math.random().toString(),
                    idDoc= idDoc,
                    title = currentState.title,
                    description = currentState.description,
                    status = currentState.status,
                    item_category = currentState.category,
                    coordinates = Pair(0.0, 0.0),
                    date_lost = Date(),
                    createdAt = "",
                    updatedAt = Date(),
                    picture = currentState.picture,
                    userId = userId.toString()
                ))
        }
    }
    fun validate(state: EditItemUiState): Boolean {
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

data class EditItemUiState(
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

sealed class EditItemEvent {
    data class TitleChange(val title: String): EditItemEvent()
    data class DescriptionChange(val description: String): EditItemEvent()
    data class StatusChange(val status: String): EditItemEvent()
    data class PictureChange(val picture: String): EditItemEvent()
    data class CategoryChange(val category: String): EditItemEvent()
    data class SaveItem(val idDoc: String): EditItemEvent()
}