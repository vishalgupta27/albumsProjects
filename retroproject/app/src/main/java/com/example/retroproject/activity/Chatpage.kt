package com.example.retroproject.activity

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RemoteViews
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.retroproject.R
import com.example.retroproject.adapter.Recyclerviewadapters
import com.example.retroproject.model.Data
import com.example.retroproject.model.Meetprofiles
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class Chatpage : AppCompatActivity() {

    var user_name: TextView? = null
    var distance: TextView? = null
    private var musics: TextView? = null
    var meet_profile_images: RecyclerView? = null
    private var itemList = ArrayList<Data>()
    lateinit var adapter: Recyclerviewadapters


    @SuppressLint("MissingInflatedId", "NotifyDataSetChanged", "RemoteViewLayout")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatpage)

        user_name = findViewById(R.id.left_text_view)
        distance = findViewById(R.id.right_text_view)
        musics = findViewById(R.id.tv_music)
        musics!!.setOnClickListener {
            val intents = Intent(this, MediaPlayersPractice::class.java)
            startActivity(intents)
        }
        meet_profile_images = findViewById(R.id.rl_images)
        val peopleRV = findViewById<RecyclerView>(R.id.peopleRV)


        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        peopleRV?.layoutManager = staggeredGridLayoutManager


        adapter =
            Recyclerviewadapters(itemList, this@Chatpage, object : Recyclerviewadapters.BtnClickListener {
                    override fun onBtnClick(position: Int, type: String) {
                        if (type == "moveToOtherScreen") {
                            val products = Intent(this@Chatpage, Profilesdetials2::class.java)
                            products.putExtra("profile_data", Gson().toJson(itemList.get(position)))
                            startActivity(products)
                        }
                    }
                })

        peopleRV.adapter = adapter

        getchat()


    }

    //  "hILdLHdd2gdlsUo1q4ifLQsBMC02","keyword":"","user_lat":26.4601011,"user_long":80.7731979,"radius":500}
    private fun getchat() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("firebase_id", "hILdLHdd2gdlsUo1q4ifLQsBMC02")
        jsonObject.addProperty("keyword", "")
        jsonObject.addProperty("user_lat", 26.4601011)
        jsonObject.addProperty("user_long", 80.7731979)
        jsonObject.addProperty("radius", 500)
        val JsonArray = JsonArray()

        Log.d("check", "getchat: $jsonObject")

        val chat = Methods.methodsinstance.getMeetListing(jsonObject)
        chat.enqueue(object : Callback<Map<Any, Any>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<Map<Any, Any>?>, response: Response<Map<Any, Any>?>
            ) {
                val chat = response.body()
                if (chat != null) {
                    Log.d("getchat", chat.toString())

                    val gson = Gson()
                    val objrespose = gson.fromJson(gson.toJson(chat), Meetprofiles::class.java)
                    if (objrespose.status == true) {
                        itemList.clear()
                        itemList.addAll(objrespose.meetProfileList)
                        adapter.notifyDataSetChanged()

                    }
                    Log.d("Meetprofiles", "onResponse:${objrespose.meetProfileList[0].user_name}")
                }
            }

            override fun onFailure(call: Call<Map<Any, Any>?>, t: Throwable) {
                Log.i("errorMessage", t.message!!)
                Toast.makeText(this@Chatpage, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}


