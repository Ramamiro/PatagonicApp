package com.example.patagonicapp.models

import androidx.room.*

@Entity(
    tableName = "orders",
    foreignKeys = [
        ForeignKey(entity = Client::class, parentColumns = ["clientId"], childColumns = ["clientId"]),
        ForeignKey(entity = Product::class, parentColumns = ["productId"], childColumns = ["productId"])
    ],
    indices = [Index("clientId"), Index("productId")]
)
data class Order (

    @PrimaryKey(autoGenerate = true)
    val orderId: Long = 0,
    val clientId: Long,
    val productId: Long,
    val quantity: Int,
    val total: Double
    )