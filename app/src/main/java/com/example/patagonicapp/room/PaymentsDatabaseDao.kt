package com.example.patagonicapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.patagonicapp.models.Payment
import com.example.patagonicapp.models.Trip
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentsDatabaseDao {
    @Query("SELECT * FROM payments")
    fun getAllPayments(): Flow<List<Payment>>

    @Insert
    suspend fun addPayment(trip: Payment)

}