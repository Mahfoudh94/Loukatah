package com.example.loukatah.data.repository

import com.example.loukatah.data.model.ItemCategory
import kotlinx.coroutines.flow.Flow


interface ItemCategoryRepository {

    fun getCategories(): Flow<List<ItemCategory>>
}
