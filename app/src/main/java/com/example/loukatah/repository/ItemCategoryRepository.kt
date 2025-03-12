package com.example.loukatah.repository

import com.example.loukatah.model.ItemCategory

object ItemCategoryRepository {
    private val categories = listOf(
        ItemCategory(
            id = "1",
            name = "Personal Items",
            icon = "https://cdn-icons-png.flaticon.com/512/1077/1077114.png"
        ),
        ItemCategory(
            id = "2",
            name = "Electronics",
            icon = "https://cdn-icons-png.flaticon.com/512/2972/2972035.png"
        ),
        ItemCategory(
            id = "3",
            name = "Documents",
            icon = "https://cdn-icons-png.flaticon.com/512/2997/2997142.png"
        )
    )

    fun getCategories(): List<ItemCategory> {
        return categories
    }
}
