package com.example.currencychanger.Databases

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "curcenci_rates")
data class CurrencuiDatabaseModel(
        @PrimaryKey(autoGenerate = true)
        var id:Int,
        var alphacod:String,
        var rates:String,
        var textcode:String
)