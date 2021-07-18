package com.example.currencychanger

import android.app.Service
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.currencychanger.Databases.CurrenciDatabaseDao
import com.example.currencychanger.Databases.CurrenciDatabse
import com.example.currencychanger.Databases.CurrencuiDatabaseModel
import com.example.currencychanger.MODEL.CurrencidataItem
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL="https://www.tayqatech.com/"
class MainActivity : AppCompatActivity(),MyAdabter.OnClickListener,WithoutWifiMyAdabter.Onclickwithoutwifi{
    var context=this
    var connectivity:ConnectivityManager?=null
    var info: NetworkInfo?=null
    lateinit var  adabter:MyAdabter
    lateinit var adabterwithoutwifi: WithoutWifiMyAdabter
    lateinit var  linearLayoutManager: LinearLayoutManager
    lateinit var  rateamount:EditText
    lateinit var  curenci_name:TextView
    lateinit var  viewmodel:CurrenciViewModel
    lateinit var  responseBody:List<CurrencidataItem>
    lateinit var dao: CurrenciDatabaseDao
    lateinit var database:CurrenciDatabse
    lateinit var databaseModel: CurrencuiDatabaseModel
    private lateinit var currenciesdatalists: List<CurrencuiDatabaseModel>
    var my_rate_amount:Double=1.0
    var my_currenci_name:String="AZN"
    var checkData:String?= null
    var internetconnect:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.setHasFixedSize(true)
        linearLayoutManager= LinearLayoutManager(this)
        recyclerView.layoutManager=linearLayoutManager
        viewmodel=ViewModelProvider(this).get(CurrenciViewModel::class.java)
        curenci_name=findViewById(R.id.currenci_name)
        rateamount=findViewById(R.id.editText)
        database=Room.databaseBuilder(applicationContext,CurrenciDatabse::class.java,"db").allowMainThreadQueries().build()
        dao=database.currenciDatabaseDao()
        checkDatafullorempty()


        checkcoonectioninternet()
        if (internetconnect==true){
            rateamount.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (p0.isNullOrBlank() || p0.isEmpty()) {
                        rateamount.setText("0")
                        var st = "0"
                        my_rate_amount = st!!.toString().toDouble()
                        bindingrecyclerview(viewmodel.currencies)

                    } else {
                        my_rate_amount = p0!!.toString().toDouble()
                        bindingrecyclerview(viewmodel.currencies)
                    }
                }


                override fun afterTextChanged(p0: Editable?) {

                }
            })
            getCurrincidata(my_currenci_name)
            getCurrencidataWithoutWifi()
            curenci_name.setText(my_currenci_name)

        }
        else {
            bindingrecyclerviewwithoutwifi()
        }


    }

    private fun checkDatafullorempty() {
        if (dao.readData().isEmpty())
        {
            checkData="empty"
        }
        else
            checkData="full"
    }

    fun checkcoonectioninternet():Boolean {
           connectivity=context.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
           if (connectivity!=null){
               info=connectivity!!.activeNetworkInfo
               if (info!=null){
                   if (info!!.state==NetworkInfo.State.CONNECTED){
                       internetconnect=true
                   }


               }
               else{
                    internetconnect=false
               }

           }
           return internetconnect
    }

    private fun getCurrencidataWithoutWifi() {
        val retBuilder=Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(CurrenciApiInterfaci::class.java)

        val retrofitdata=retBuilder.getCurrencidata(my_currenci_name)
        retrofitdata.enqueue(object : Callback<List<CurrencidataItem>?> {
            override fun onResponse(
                    call: Call<List<CurrencidataItem>?>,
                    response: Response<List<CurrencidataItem>?>
            ) {
                responseBody=response.body()!!
                viewmodel.currencies=responseBody
                for (i in 1..responseBody.size)
                if(checkData.equals("empty")){

                    databaseModel.alphacod=responseBody.get(i-1).alphaCode
                    databaseModel.id=i
                    databaseModel.rates=responseBody.get(i-1).rate.toString()
                    getCurrincidata(my_currenci_name)

                }
                else if(checkData.equals("full")){

                    databaseModel.alphacod=responseBody.get(i-1).alphaCode
                    databaseModel.id=i
                    databaseModel.rates=responseBody.get(i-1).rate.toString()
                    getCurrincidata(my_currenci_name)
                }

               checkData.equals("full")

            }

            override fun onFailure(call: Call<List<CurrencidataItem>?>, t: Throwable){
                Toast.makeText(applicationContext,"Failed", Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun bindingrecyclerview(currencies: List<CurrencidataItem>){
        adabter= MyAdabter(currencies,this@MainActivity,my_rate_amount)
        adabter.notifyDataSetChanged()
        recyclerView.adapter=adabter

    }
    private fun bindingrecyclerviewwithoutwifi(){
        adabterwithoutwifi= WithoutWifiMyAdabter(currenciesdatalists,this@MainActivity,my_rate_amount)
        adabterwithoutwifi.notifyDataSetChanged()
        recyclerView.adapter=adabterwithoutwifi

    }

    private fun getCurrincidata(mystring_currenci:String) {
        val retBuilder=Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CurrenciApiInterfaci::class.java)

        val retrofitdata=retBuilder.getCurrencidata(my_currenci_name)
        retrofitdata.enqueue(object : Callback<List<CurrencidataItem>?> {
            override fun onResponse(
                call: Call<List<CurrencidataItem>?>,
                response: Response<List<CurrencidataItem>?>
            ) {

                responseBody=response.body()!!
                viewmodel.currencies=responseBody
                bindingrecyclerview(viewmodel.currencies)

            }

            override fun onFailure(call: Call<List<CurrencidataItem>?>, t: Throwable){
               Toast.makeText(applicationContext,"Failed", Toast.LENGTH_LONG).show()
            }
        })
        Handler(Looper.getMainLooper()).postDelayed({
            getCurrincidata(my_currenci_name)
        },2000)
    }

    override fun onclick(p: Int) {
        currenci_name.text=responseBody[p].alphaCode
        my_currenci_name=responseBody[p].alphaCode
        getCurrincidata(my_currenci_name)

    }

    override fun clickt(p:Int){
        currenci_name.text=responseBody[p].alphaCode
        for (i in 1..responseBody.size)
            if(checkData.equals("empty")){
                databaseModel.textcode=curenci_name.text.toString()
                databaseModel.alphacod=responseBody.get(i-1).alphaCode
                databaseModel.id=i
                databaseModel.rates=responseBody.get(i-1).rate.toString()
                getCurrincidata(my_currenci_name)

            }
            else if(checkData.equals("full")){
                databaseModel.textcode=curenci_name.text.toString()
                databaseModel.alphacod=responseBody.get(i-1).alphaCode
                databaseModel.id=i
                databaseModel.rates=responseBody.get(i-1).rate.toString()
                getCurrincidata(my_currenci_name)
            }

        checkData.equals("full")

    }
}