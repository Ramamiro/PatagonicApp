package com.example.patagonicapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.patagonicapp.models.Debt
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtsDatabaseDao {
    @Query("SELECT * FROM debts")
    fun getAllDebts(): Flow<List<Debt>>

    @Insert
    suspend fun addDebt(trip: Debt)

}