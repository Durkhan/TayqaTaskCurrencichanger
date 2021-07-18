package com.example.currencychanger.MODEL

data class CurrencidataItem(
    val alphaCode: String,
    val code: String,
    val date: String,
    val inverseRate: Double,
    val name: String,
    val numericCode: String,
    val rate: Double
)