package com.example.loukatah.domain.usecase

import com.example.loukatah.data.model.Item
import com.example.loukatah.data.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetItemsUseCase @Inject constructor(
    private val itemRepository: ItemRepository
)
{
    operator fun invoke(): Flow<List<Item>> = itemRepository.getItems()
}
