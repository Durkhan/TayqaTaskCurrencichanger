package com.example.currencychanger


import com.example.currencychanger.MODEL.CurrencidataItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciApiInterfaci {

    @GET("rates.php?")
    fun getCurrencidata(@Query("base")currency_name:String): Call<List<CurrencidataItem>>

}