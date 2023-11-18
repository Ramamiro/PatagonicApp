package com.example.patagonicapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.patagonicapp.models.Product
import com.example.patagonicapp.models.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrdersDatabaseDao {

    @Query("SELECT * FROM orders")
    fun getAllOrders(): Flow<List<Order>>

    @Insert
    suspend fun addOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order:Order)

}