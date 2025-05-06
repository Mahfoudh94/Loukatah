package com.example.loukatah.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loukatah.domain.usecase.DeleteItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteItemViewModel @Inject constructor(
    private val deleteItemUseCase: DeleteItemUseCase
) : ViewModel() {

    fun deleteItem(id: String) {
        viewModelScope.launch {
            deleteItemUseCase.invoke(id)
        }
    }
}