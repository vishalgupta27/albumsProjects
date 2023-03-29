package com.example.albums

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


private const val BASE_Url = "https://api.openweathermap.org/data/2.5/"

interface ApiInterface {
    @GET("forecast")
    fun openWeather(
        @Query("q") q: String?,
        @Query("appid") appid: String?
    ): Call<Map<Any, Any>?>
}

object Methods {

    val methodsinstance: ApiInterface

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        methodsinstance = retrofit.create(ApiInterface::class.java)
    }
}

