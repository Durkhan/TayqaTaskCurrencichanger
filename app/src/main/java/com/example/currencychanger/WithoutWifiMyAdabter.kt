package com.example.currencychanger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencychanger.Databases.CurrencuiDatabaseModel

class WithoutWifiMyAdabter(private var list:List<CurrencuiDatabaseModel>,var click:Onclickwithoutwifi,var change_amount:Double):RecyclerView.Adapter<WithoutWifiMyAdabter.MyNewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNewHolder {
       var itemView=LayoutInflater.from(parent.context).inflate(R.layout.currencieslist,parent,false)
        return MyNewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyNewHolder, position: Int) {
        holder.name?.text=list.get(position).alphacod
        holder.rate?.text=(list.get(position).rates.toDouble()*change_amount).toString()
        holder.cardview.setOnClickListener{
            click.clickt(position)
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }
  inner class MyNewHolder(view: View):RecyclerView.ViewHolder(view){
      var myItemView:View?=null
      var name: TextView? = null
      var rate: TextView? = null
      var cardview: CardView

      init {
          myItemView = view
          name = view.findViewById(R.id.name)
          rate = view.findViewById(R.id.rate)
          cardview=view.findViewById(R.id.cardviewclick)
      }
  }

    interface Onclickwithoutwifi {
         fun clickt(p:Int)
    }
}