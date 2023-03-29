package com.example.albums

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.albums.adapters.WeathersAdapter
import com.google.gson.Gson

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeathersAPI : AppCompatActivity() {

    private var itemList = ArrayList<InforWeat>()
    lateinit var adapter: WeathersAdapter
    var name: TextView? = null
    var sunrise: TextView? = null
    var sunset: TextView? = null
    var lats: TextView? = null
    var long: TextView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weathrs_api)
        name = findViewById(R.id.tv_Weaname)
        sunrise = findViewById(R.id.tv_sunrise)
        sunset = findViewById(R.id.tv_sunset)
        long = findViewById(R.id.tv_long)
        lats = findViewById(R.id.tv_lats)


/*        val weatherRv = findViewById<RecyclerView>(R.id.recyclerView)
        val linearLayoutManager = LinearLayoutManager(this)
        weatherRv?.layoutManager = linearLayoutManager

        adapter = WeathersAdapter(itemList, this, object : WeathersAdapter.BtnClickListener {
            override fun onBtnClick(position: Int, type: String) {
            }
        })
        weatherRv.adapter = adapter*/
        getWeather()
    }


    fun convertDateTime(time: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val convertedTIME = Date(time)
        return sdf.format(convertedTIME)
    }


    private fun getWeather() {

        val chat =
            Methods.methodsinstance.openWeather("Kolkata", "3950dee4428eb6f2dcd3ebf07f8c73bb")
        chat.enqueue(object : Callback<Map<Any, Any>?> {
            @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation", "SetTextI18n")
            override fun onResponse(
                call: Call<Map<Any, Any>?>, response: Response<Map<Any, Any>?>
            ) {
                val chat = response.body()
                if (chat != null) {
                    Log.d("getchat57", chat.toString())
                    val gson = Gson()
                    val objrespose = gson.fromJson(gson.toJson(chat), InforWeat::class.java)
                    name?.text = "Name :  " + objrespose.city.name
                    sunrise?.text = "Sunrise :  " + convertDateTime(objrespose.city.sunrise)
                    sunset?.text = "Sunset :  " + convertDateTime(objrespose.city.sunset)
                    long?.text = "Longitude :  " + objrespose.city.coord?.lon.toString()
                    lats?.text = "Latitude :  " + objrespose.city.coord?.lat.toString()


                    Log.d("getchat57", "onResponse:${objrespose.city.name} ")


                }
            }

            override fun onFailure(call: Call<Map<Any, Any>?>, t: Throwable) {
                Log.i("errorMessage", t.message!!)

            }
        })
    }
}