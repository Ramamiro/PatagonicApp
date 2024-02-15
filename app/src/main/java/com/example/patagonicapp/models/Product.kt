package com.example.patagonicapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productId: Long = 0,

    @ColumnInfo(name = "name")
    val productName: String,

    @ColumnInfo(name = "price")
    val pricePerKg: Double,

    val kgPerUnit: Double
    )
