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
    val error: String? = null
)

class ItemViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

   init {
        getItems(4 , "")
    }

    fun getItems(X : Int , S : String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                delay(2000)


                if(X == 2){
                 val items = ItemRepository.getCategoryE(S)
                    _uiState.value = _uiState.value.copy(items = items, isLoading = false)
                }else if (X == 3){
                   val items = ItemRepository.getCategoryD(S)
                    _uiState.value = _uiState.value.copy(items = items, isLoading = false)
                }else if (X == 1){
                    val items = ItemRepository.getCategoryPI(S)
                    _uiState.value = _uiState.value.copy(items = items, isLoading = false)
                }
                else{
                    val items = ItemRepository.getItems()
                    _uiState.value = _uiState.value.copy(items = items, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }
}
