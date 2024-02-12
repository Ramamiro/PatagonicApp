package com.example.patagonicapp.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.patagonicapp.models.*
import com.example.patagonicapp.room.ClientsDatabaseDao
import com.example.patagonicapp.room.OrdersDatabaseDao
import com.example.patagonicapp.room.ProductsDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(
    entities = [Client::class, Product::class, Order::class, Location::class, Debt::class, Payment::class, Trip::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun clientsDao(): ClientsDatabaseDao
    abstract fun productsDao(): ProductsDatabaseDao
    abstract fun ordersDao(): OrdersDatabaseDao
    abstract fun locationsDao(): LocationsDatabaseDao
    abstract fun debtsDao(): DebtsDatabaseDao
    abstract fun paymentsDao(): PaymentsDatabaseDao
    abstract fun tripsDao(): TripsDatabaseDao
}