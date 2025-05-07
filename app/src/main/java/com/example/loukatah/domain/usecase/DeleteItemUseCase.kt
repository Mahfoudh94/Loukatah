package com.example.loukatah.domain.usecase

import com.example.loukatah.data.repository.ItemRepository
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(private val itemRepository: ItemRepository) {
    suspend fun invoke(id: String) {
        itemRepository.deleteItem(id)
    }
}