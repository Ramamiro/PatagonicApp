package com.example.patagonicapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.patagonicapp.models.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationsDatabaseDao {

    @Query("SELECT * FROM locations")
    fun getAllLocations() : Flow<List<Location>>

    @Insert
    suspend fun addLocation(location: Location)

    @Query("DELETE FROM locations WHERE locationId = :locationId")
    suspend fun deleteLocation(locationId: Long)
}