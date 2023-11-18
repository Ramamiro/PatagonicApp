package com.example.patagonicapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="clients")
data class Client(

    @PrimaryKey(autoGenerate = true)
    val clientId: Long = 0,

    @ColumnInfo(name = "clientName")
    val clientName: String,

    @ColumnInfo(name= "clientBusiness")
    val clientBusiness: String,

    @ColumnInfo(name= "clientStatus")
    val clientStatus: ClientStatus = ClientStatus.INACTIVE
    )

enum class ClientStatus {
    INACTIVE,
    PENDING,
    DELIVERED,
    CONCLUDED,
    CANCELLED
}