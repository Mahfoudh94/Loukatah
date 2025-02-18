package com.example.loukatah.model

data class Item(
    val id: String,
    val title: String,
    val description: String,
    val status: String,
    val picture: String?,
    val item_category: String,
    val coordinates: Pair<Double, Double>,
    val date_lost: String?,
    val createdAt: String,
    val updatedAt: String
)
