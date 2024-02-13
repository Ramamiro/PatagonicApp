package com.example.patagonicapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")

data class Trip(

    @PrimaryKey(autoGenerate = true)
    val tripId: Long = 0,

    val active: Boolean,

    val name: String,

)
