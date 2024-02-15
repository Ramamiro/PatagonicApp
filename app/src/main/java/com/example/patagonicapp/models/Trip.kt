package com.example.patagonicapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")

data class Trip(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val active: Boolean = true,

    val name: String,
)
