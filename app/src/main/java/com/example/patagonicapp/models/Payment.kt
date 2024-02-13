package com.example.patagonicapp.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.patagonicapp.PaymentType

@Entity(
    tableName = "payments",
    foreignKeys = [
        ForeignKey(
            entity = Client::class,
            childColumns = ["clientId"],
            parentColumns = ["clientId"]
        ),
//        ForeignKey(
//            entity = Trip::class,
//            childColumns = ["tripId"],
//            parentColumns = ["tripId"]
//        )
                  ],
    indices = [
        Index("clientId")
//        , Index("tripId")
    ]
)

data class Payment(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

//    val tripId: Long,

    val clientId: Long,

    val type: PaymentType,

    val amount: Double

)
