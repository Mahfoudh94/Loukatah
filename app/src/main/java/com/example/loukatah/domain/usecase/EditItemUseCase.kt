package com.example.loukatah.domain.usecase

import com.example.loukatah.data.model.Item
import com.example.loukatah.data.repository.ItemRepository
import javax.inject.Inject

class EditItemUseCase @Inject constructor(private val itemRepository: ItemRepository) {

    suspend fun invoke(item: Item) {
        itemRepository.updateItem(item)
    }
}