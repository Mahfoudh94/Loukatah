package com.example.loukatah.model

import androidx.compose.ui.graphics.vector.ImageVector

data class ItemCategory(
    val id: String,
    val name: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)
