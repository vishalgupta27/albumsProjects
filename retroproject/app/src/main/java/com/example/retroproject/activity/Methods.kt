package com.example.retroproject.activity

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

private const val BASE_URL = "https://hiferr.com/backend/public/api/user/"

interface Methodsinterface {
    /*@POST("profile")
    fun getUserData(
        @Query("user_name") name: String?,
        @Query("distance") distance: String?,
        @Query("meet_profile_image") meet_profile_image: String?, ): Call<Map<Any,Any>?>?*/
    @POST("profile")
    fun getMeetListing( @Body text: JsonElement): Call<Map<Any,Any>?>

}

object Methods {
    val methodsinstance: Methodsinterface

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        methodsinstance = retrofit.create(Methodsinterface::class.java)
    }
}