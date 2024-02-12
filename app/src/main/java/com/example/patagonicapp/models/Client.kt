package com.example.patagonicapp.models

import androidx.room.*
import com.example.patagonicapp.ClientStatus

@Entity(tableName="clients",
foreignKeys = [ForeignKey(entity= Location::class, childColumns = ["locationId"], parentColumns = ["locationId"])],
indices= [Index("locationId")])

data class Client(

    @PrimaryKey(autoGenerate = true)
    val clientId: Long = 0,

    @ColumnInfo(name = "clientName")
    val clientName: String,

    @ColumnInfo(name= "clientBusiness")
    val clientBusiness: String,

    @ColumnInfo(name= "clientStatus")
    val clientStatus: ClientStatus = ClientStatus.INACTIVE,

    val locationId: Long
    )

