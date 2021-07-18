package com.example.currencychanger.Databases

import androidx.room.*

@Dao
interface CurrenciDatabaseDao {

    @Query(value = "SELECT* FROM curcenci_rates")
         fun readData():List<CurrencuiDatabaseModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
         fun addData(currenci_databasemodel:CurrencuiDatabaseModel)

    @Update
         fun updateData(currenci_databasemodel:CurrencuiDatabaseModel)

}