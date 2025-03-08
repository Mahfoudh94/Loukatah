package com.example.loukatah.data.repository

import com.example.loukatah.data.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.Date
import javax.inject.Inject


class ItemRepositoryImpl @Inject constructor() : ItemRepository {
    private val items = mutableListOf(
        Item(
            id = "1",
            title = "Lost Wallet",
            description = "A black leather wallet lost near the park.",
            status = "Lost",
            picture = "https://images.unsplash.com/photo-1613243555978-636c48dc653c?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80",
            item_category = "Personal Items",
            coordinates = Pair(34.0522, -118.2437),
            date_lost = Date(),
            createdAt = Date().toString(),
            updatedAt = Date()
        ),
        Item(
            id = "2",
            title = "Lost Phone",
            description = "A white iPhone lost in the cafe.",
            status = "Lost",
            picture = "https://images.unsplash.com/photo-1603796846097-bee99e4a601f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1974&q=80",
            item_category = "Electronics",
            coordinates = Pair(34.0522, -118.2437),
            date_lost = Date(),
            createdAt = Date().toString(),
            updatedAt = Date()
        ),
        Item(
            id = "3",
            title = "Lost Backpack",
            description = "A blue backpack left on the bus.",
            status = "Lost",
            picture = "https://images.unsplash.com/photo-1566150902887-e24c4a5d9d07?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80",
            item_category = "Personal Items",
            coordinates = Pair(34.0522, -118.2437),
            date_lost = Date(),
            createdAt = Date().toString(),
            updatedAt = Date()
        ),
        Item(
            id = "4",
            title = "Found Laptop",
            description = "A silver MacBook found in the library.",
            status = "Found",
            picture = "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2071&q=80",
            item_category = "Electronics",
            coordinates = Pair(34.0522, -118.2437),
            date_lost = Date(),
            createdAt = Date().toString(),
            updatedAt = Date()
        ),
        Item(
            id = "5",
            title = "Found Passport",
            description = "A passport found at the airport.",
            status = "Found",
            picture = "https://images.unsplash.com/photo-1551806235-f1c8d1f2d0b0?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80",
            item_category = "Documents",
            coordinates = Pair(34.0522, -118.2437),
            date_lost = Date(),
            createdAt = Date().toString(),
            updatedAt = Date()
        )
    )

    private val _itemsFlow = MutableSharedFlow<List<Item>>(replay = 1)

    init {
        _itemsFlow.tryEmit(items.toList())
    }

    override fun getItems(): Flow<List<Item>> = _itemsFlow

    override suspend fun addItem(item: Item) {
        items.add(item)
        _itemsFlow.emit(items.toList())
    }
}
