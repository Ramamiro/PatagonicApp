package com.example.patagonicapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.patagonicapp.models.Trip
import kotlinx.coroutines.flow.Flow

@Dao
interface TripsDatabaseDao {
    @Query("SELECT * FROM trips")
    fun getAllTrips(): Flow<List<Trip>>

    @Insert
    suspend fun addTrip(trip: Trip)

    @Update
    suspend fun updateTrip(trip: Trip)
}