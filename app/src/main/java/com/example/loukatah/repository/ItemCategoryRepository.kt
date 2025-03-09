package com.example.loukatah.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.outlined.AllInbox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PhoneAndroid
import com.example.loukatah.model.ItemCategory

object ItemCategoryRepository {
    private val categories = listOf(
        ItemCategory(
            id = "1",
            name = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon =  Icons.Outlined.Home,
        ),
        ItemCategory(
            id = "2",
            name = "Map",
            selectedIcon = Icons.Filled.LocationOn,
            unselectedIcon =  Icons.Outlined.LocationOn,
        ),
        ItemCategory(
            id = "3",
            name = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon =  Icons.Outlined.Person,
        )
    )

    fun getCategories(): List<ItemCategory> {
        return categories
    }
}
