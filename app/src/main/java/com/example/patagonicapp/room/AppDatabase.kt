package com.example.patagonicapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.patagonicapp.models.Client
import com.example.patagonicapp.models.Product
import com.example.patagonicapp.models.Order
import com.example.patagonicapp.room.ClientsDatabaseDao
import com.example.patagonicapp.room.OrdersDatabaseDao
import com.example.patagonicapp.room.ProductsDatabaseDao

@Database(
    entities=[Client::class, Product::class, Order::class],
    version=1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun clientsDao(): ClientsDatabaseDao
    abstract fun productsDao(): ProductsDatabaseDao
    abstract fun ordersDao(): OrdersDatabaseDao

}