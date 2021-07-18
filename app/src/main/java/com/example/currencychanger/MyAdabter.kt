package com.example.currencychanger

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencychanger.MODEL.CurrencidataItem

class MyAdabter(private val ratesList:List<CurrencidataItem>, var click: OnClickListener, var change_rate_amount:Double):RecyclerView.Adapter<MyAdabter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemview=LayoutInflater.from(parent.context).inflate(R.layout.currencieslist,parent,false)
        return  ViewHolder(itemview)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.name?.text=ratesList[position].alphaCode
         holder.rate?.text=(ratesList[position].rate.toString().toDouble()*(change_rate_amount)).toString()
         holder.cardview.setOnClickListener{
              click.onclick(position)
        }

    }

    override fun getItemCount(): Int {
        return ratesList.size
    }
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var myItemView:View?=null
        var name:TextView? = null
        var rate:TextView? = null
        var cardview:CardView

        init {
            myItemView = view
            name = view.findViewById(R.id.name)
            rate = view.findViewById(R.id.rate)
            cardview=view.findViewById(R.id.cardviewclick)
        }
    }

    interface OnClickListener {
        fun onclick(p:Int)
    }

}


