package com.example.patagonicapp.models

import androidx.room.*

@Entity(
    tableName = "orders",
    foreignKeys = [
        ForeignKey(
            entity = Client::class,
            parentColumns = ["clientId"],
            childColumns = ["clientId"]
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["productId"],
            childColumns = ["productId"]
        ),
        ForeignKey(
            entity = Trip::class,
            childColumns = ["tripId"],
            parentColumns = ["id"]
        )
    ],
    indices = [Index("clientId"), Index("productId"),Index("tripId")]
)
data class Order(

    @PrimaryKey(autoGenerate = true)
    val orderId: Long = 0,
    val clientId: Long,
    val productId: Long,
    val tripId: Long,
    val quantity: Int,
    val total: Double,
    val weight: Double,

    )