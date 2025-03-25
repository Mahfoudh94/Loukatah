package com.example.loukatah.data.repository

import com.example.loukatah.data.model.Item
import kotlinx.coroutines.flow.Flow


interface ItemRepository {

    fun getItems(): Flow<List<Item>>

    suspend fun addItem(item: Item)

}
