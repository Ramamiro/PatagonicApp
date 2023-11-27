package com.example.patagonicapp.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.patagonicapp.models.Client
import com.example.patagonicapp.models.Location
import com.example.patagonicapp.models.Product
import com.example.patagonicapp.models.Order
import com.example.patagonicapp.room.ClientsDatabaseDao
import com.example.patagonicapp.room.OrdersDatabaseDao
import com.example.patagonicapp.room.ProductsDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(
    entities = [Client::class, Product::class, Order::class, Location::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun clientsDao(): ClientsDatabaseDao
    abstract fun productsDao(): ProductsDatabaseDao
    abstract fun ordersDao(): OrdersDatabaseDao
    abstract fun locationsDao(): LocationsDatabaseDao

}