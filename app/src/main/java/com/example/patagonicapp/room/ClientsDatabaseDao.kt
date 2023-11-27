package com.example.patagonicapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.patagonicapp.models.Client
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientsDatabaseDao {

    @Query("SELECT * FROM clients")
    fun getAllClients(): Flow<List<Client>>

    @Insert
    suspend fun addClient(client: Client)

    @Delete
    suspend fun deleteClient(client: Client)

    @Update
    suspend fun updateClient(client: Client)
}