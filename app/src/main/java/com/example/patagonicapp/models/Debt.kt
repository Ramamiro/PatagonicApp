package com.example.patagonicapp.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "debts",
    foreignKeys = [
        ForeignKey(
            entity = Client::class,
            childColumns = ["clientId"],
            parentColumns = ["clientId"]
        ),
        ForeignKey(
            entity = Trip::class,
            childColumns = ["tripId"],
            parentColumns = ["tripId"]
        )],
    indices = [
        Index("clientId"),
        Index("tripId")]
)


data class Debt(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val tripId: Long,

    val clientId: Long,

    val amount: Double,

    val canceled: Boolean
)
