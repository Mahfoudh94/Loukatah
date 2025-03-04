package com.example.loukatah.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.outlined.AllInbox
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.PhoneAndroid
import com.example.loukatah.model.ItemCategory

object ItemCategoryRepository {
    private val categories = listOf(
        ItemCategory(
            id = "0",
            name = "All",
            selectedIcon = Icons.Filled.AllInbox,
            unselectedIcon =  Icons.Outlined.AllInbox,
        ),
        ItemCategory(
            id = "1",
            name = "Personal Items",
            selectedIcon = Icons.Filled.People,
            unselectedIcon =  Icons.Outlined.People,
        ),
        ItemCategory(
            id = "2",
            name = "Electronics",
            selectedIcon = Icons.Filled.PhoneAndroid,
            unselectedIcon =  Icons.Outlined.PhoneAndroid,
        ),
        ItemCategory(
            id = "3",
            name = "Documents",
            selectedIcon = Icons.Filled.Newspaper,
            unselectedIcon =  Icons.Outlined.Newspaper,
        )
    )

    fun getCategories(): List<ItemCategory> {
        return categories
    }
}
