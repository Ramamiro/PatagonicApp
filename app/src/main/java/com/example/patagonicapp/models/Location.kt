package com.example.patagonicapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey(autoGenerate = true)
    val locationId: Long = 0,

    val locationName:String
)
