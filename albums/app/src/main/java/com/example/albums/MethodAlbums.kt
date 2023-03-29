package com.example.albums

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


val BASE_URL = "https://jsonplaceholder.typicode.com/"   // photos

interface MethodsAlbumsInterface {

    @GET("photos")
    fun getPhotos(): Call<AlbumsModelsItem?>?
  }

object MethodAlbums {
    val methodsinstance: MethodsAlbumsInterface

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        methodsinstance = retrofit.create(MethodsAlbumsInterface::class.java)
    }
}