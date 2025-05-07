package com.example.loukatah.data.repository

import com.example.loukatah.data.model.Item
import kotlinx.coroutines.flow.Flow


interface ItemRepository {

    fun getItems(): Flow<List<Item>>

    suspend fun addItem(item: Item)

    suspend fun deleteItem(itemId: String)
    suspend fun updateItem(item: Item)
    fun getItemById(itemId: String): Flow<Item?>
    fun searchItems(query: String, category: String?, status: String?): Flow<List<Item>>
}
