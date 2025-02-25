package com.example.loukatah.repository

import com.example.loukatah.model.Item
import java.util.Date

object ItemRepository {
    private val items = listOf(
        Item(
            id = "1",
            title = "Lost Wallet",
            description = "A black leather wallet lost near the park.",
            status = "Lost",
            picture = "https://i.postimg.cc/NG9SnZY8/black-leather-wallet.webp",
            item_category = "Personal Items",
            coordinates = Pair(34.0522, -118.2437),
            location = "Central Park",
            date_lost = Date(),
            createdAt = Date(),
            updatedAt = Date()
        ),
        Item(
            id = "2",
            title = "Lost Phone",
            description = "A black iPhone lost in the cafe.",
            status = "Lost",
            picture = "https://i.postimg.cc/fRj6KXs6/iphone.jpg",
            item_category = "Electronics",
            coordinates = Pair(34.0522, -118.2437),
            location = "Central Park",
            date_lost = Date(),
            createdAt = Date(),
            updatedAt = Date()
        ),
        Item(
            id = "3",
            title = "Lost Backpack",
            description = "A blue backpack left on the bus.",
            status = "Lost",
            picture = "https://i.postimg.cc/Gm0M6pzd/Backpack.jpg",
            item_category = "Personal Items",
            coordinates = Pair(34.0522, -118.2437),
            location = "Central Park",
            date_lost = Date(),
            createdAt = Date(),
            updatedAt = Date()
        ),
        Item(
            id = "4",
            title = "Found Laptop",
            description = "A silver MacBook found in the library.",
            status = "Found",
            picture = "https://i.postimg.cc/pV6cYCF9/macbook.jpg",
            item_category = "Electronics",
            coordinates = Pair(34.0522, -118.2437),
            location = "Central Park",
            date_lost = Date(),
            createdAt = Date(),
            updatedAt = Date()
        ),
        Item(
            id = "5",
            title = "Found Passport",
            description = "A passport found at the airport.",
            status = "Found",
            picture = "https://i.postimg.cc/3RB5xvSX/passport.png",
            item_category = "Documents",
            coordinates = Pair(34.0522, -118.2437),
            location = "Central Park",
            date_lost = Date(),
            createdAt = Date(),
            updatedAt = Date()
        ),
        Item(
            id = "7",
            title = "Found Ring",
            description = "Golden ring.",
            status = "Found",
            picture = "https://i.postimg.cc/MZmKncMr/Ring.webp",
            item_category = "Documents",
            coordinates = Pair(34.0522, -118.2437),
            location = "Central Park",
            date_lost = Date(),
            createdAt = Date(),
            updatedAt = Date()
        ),
        Item(
            id = "8",
            title = "Lost Money",
            description = "A Money found at the main street.",
            status = "Lost",
            picture = "https://i.postimg.cc/SRMQbnhr/money.jpg",
            item_category = "Documents",
            coordinates = Pair(34.0522, -118.2437),
            location = "Main Street",
            date_lost = Date(),
            createdAt = Date(),
            updatedAt = Date()
        )
    )

    fun getItems(searchQuery:String? = null): List<Item> {
        if(searchQuery.isNullOrEmpty()){
            return items
        }else{
            return items.filter { it.title.contains(searchQuery, ignoreCase = true) }
        }
    }
}
