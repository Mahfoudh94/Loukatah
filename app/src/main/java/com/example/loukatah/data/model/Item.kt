package com.example.loukatah.data.model

import java.util.Date


data class Item(
    val id: String = "",
    val idDoc:String="",
    val title: String = "",
    val description: String = "",
    val status: String = "",
    val picture: String? = null,
    val item_category: String = "",
    val coordinates: Pair<Double, Double> = Pair(0.0, 0.0),
    val date_lost: Date = Date(),
    val createdAt: String = Date().toString(),
    val updatedAt: Date = Date(),
    val userId: String = ""
) {
    /**
     * تحويل الكائن إلى Map لاستخدامه مع Firestore
     */
    fun toMap(): Map<String, Any> {
        return mutableMapOf<String, Any>().apply {
            put("id", id)
            put("title", title)
            put("description", description)
            put("status", status)
            put("picture", picture ?: "")
            put("item_category", item_category)
            put("latitude", coordinates.first)
            put("longitude", coordinates.second)
            put("date_lost", date_lost.time)
            put("createdAt", createdAt)
            put("updatedAt", updatedAt.time)
            put("userId", userId)
        }.toMap()
    }

    companion object {
        /**
         * تحويل Map من Firestore إلى كائن Item
         * @throws ClassCastException إذا كانت بيانات الخريطة غير صالحة
         */
        fun fromMap(map: Map<String, Any>,idDoc:String): Item {
            return try {
                Item(
                    id = map["id"]?.toString() ?: "",
                    idDoc=idDoc,
                    title = map["title"]?.toString() ?: "",
                    description = map["description"]?.toString() ?: "",
                    status = map["status"]?.toString() ?: "",
                    picture = map["picture"]?.toString(),
                    item_category = map["item_category"]?.toString() ?: "",
                    coordinates = Pair(
                        (map["latitude"] as? Double) ?: 0.0,
                        (map["longitude"] as? Double) ?: 0.0
                    ),
                    date_lost = Date((map["date_lost"] as? Long) ?: 0),
                    createdAt = map["createdAt"]?.toString() ?: Date().toString(),
                    updatedAt = Date((map["updatedAt"] as? Long) ?: 0),
                    userId = map["userId"]?.toString() ?: ""
                )
            } catch (e: Exception) {
                throw IllegalArgumentException("Failed to parse Item from map", e)
            }
        }
    }
}