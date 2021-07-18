package com.example.currencychanger

import androidx.lifecycle.ViewModel
import com.example.currencychanger.MODEL.CurrencidataItem

class CurrenciViewModel:ViewModel() {
    lateinit var currencies:List<CurrencidataItem>
}