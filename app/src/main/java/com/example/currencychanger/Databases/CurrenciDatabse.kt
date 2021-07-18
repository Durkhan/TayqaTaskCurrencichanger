package com.example.currencychanger.Databases

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase
import kotlin.reflect.KClass

@Database(entities =[CurrencuiDatabaseModel::class],version = 1)
abstract class CurrenciDatabse:RoomDatabase() {
       abstract fun currenciDatabaseDao():CurrenciDatabaseDao
}