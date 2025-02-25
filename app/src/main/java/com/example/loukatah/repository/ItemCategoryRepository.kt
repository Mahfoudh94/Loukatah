package com.example.loukatah.repository

import com.example.loukatah.model.ItemCategory

object ItemCategoryRepository {
    private val categories = listOf(
        ItemCategory(
            id = "1",
            name = "Personal Items",
            icon = "https://img.icons8.com/material-sharp/24/user.png"
        ),
        ItemCategory(
            id = "2",
            name = "Electronics",
            icon = "https://img.icons8.com/material/24/smartphone.png"
        ),
        ItemCategory(
            id = "3",
            name = "Documents",
            icon = "https://img.icons8.com/material-outlined/24/document--v1.png"
        )
    )

    fun getCategories(): List<ItemCategory> {
        return categories
    }
}
