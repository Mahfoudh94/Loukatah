
package com.example.loukatah.model

import android.health.connect.datatypes.BloodPressureRecord.BloodPressureMeasurementLocation
import java.util.Date

data class Item(
    val id: String,
    val title: String,
    val description: String,
    val status: String,
    val picture: String?,
    val item_category: String,
    val coordinates: Pair<Double, Double>,
    val date_lost: Date,
    val createdAt: Date,
    val updatedAt: Date,
    val name: String="",
    val location: String?=null
)
