package com.example.loukatah.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.outlined.AllInbox
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.PhoneAndroid
import com.example.loukatah.data.model.ItemCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class ItemCategoryRepositoryImpl @Inject constructor() : ItemCategoryRepository {
    private val categories = mutableListOf(
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

    private val _categoryFlow = MutableSharedFlow<List<ItemCategory>>(replay = 1)

    init {
        _categoryFlow.tryEmit(categories.toList())
    }



    override fun getCategories(): Flow<List<ItemCategory>> = _categoryFlow
}
