package com.example.patagonicapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.patagonicapp.models.Product
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductsDatabaseDao {

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE productId = :requestedProductId ")
    fun getProductById(requestedProductId: Long): Flow<Product>

    @Insert
    suspend fun addProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

}