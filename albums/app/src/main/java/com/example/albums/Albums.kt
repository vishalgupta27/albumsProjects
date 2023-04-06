package com.example.albums

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.albums.adapters.AlbumsAdapters
import com.example.albums.model.AlbumsModelsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Albums : AppCompatActivity() {
    lateinit var adapters: AlbumsAdapters
    private var albumsModelsList: ArrayList<AlbumsModelsItem> = ArrayList()



    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)
        val recyclerview2 = findViewById<RecyclerView>(R.id.rle_albums)
        Log.d("checksactivity", "onCreate: ")

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerview2.layoutManager = linearLayoutManager
        adapters = AlbumsAdapters( albumsModelsList,this)


        val albums =MethodAlbums.methodsinstance.getPhotos()
        Log.d("getchat", albums.toString())
        albums!!.enqueue(object : Callback<AlbumsModelsItem?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<AlbumsModelsItem?>, response: Response<AlbumsModelsItem?>)
            {
                val albums = response.body()
                if (albums != null) {
                    Log.d("photos>>", albums.toString())

                }
            }

            override fun onFailure(call: Call<AlbumsModelsItem?>, t: Throwable) {
                Log.i("errorMessage", t.message!!)
                Toast.makeText(this@Albums, t.message, Toast.LENGTH_SHORT).show()
            }
        })

    }

    }





