package com.example.retroproject.model

import com.google.gson.annotations.SerializedName

data class Meetprofiles(
    @SerializedName("data") var meetProfileList : ArrayList<Data> = arrayListOf(),
    @SerializedName("status") var status: Boolean? = null,

    )



data class UserInterests(
    @SerializedName("id") val id: Int,
    @SerializedName("keyword") val keyword: String,
    @SerializedName("isSelected") val isSelected: Boolean?=false

)

data class Data(
    @SerializedName("about_me") val about_me: String,
    @SerializedName("city") val city: String,
    @SerializedName("contact_no") val contact_no: String,
    @SerializedName("deleted_at") val deleted_at: Int,
    @SerializedName("distance") val distance: String,
    @SerializedName("dob") val dob: String,
    @SerializedName("drinking_habit") val drinking_habit: String,
    @SerializedName("drugs") val drugs: String,
    @SerializedName("email") val email: String,
    @SerializedName("employment") val employment: String,
    @SerializedName("ethnicity") val ethnicity: String,
    @SerializedName("firebase_id") val firebase_id: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("id") val id: Int,
    @SerializedName("is_matched") val is_matched: String,
    @SerializedName("languagearr") val languagearr: List<String>,
    @SerializedName("meet_profile_image") val meet_profile_image: List<String>,
    @SerializedName("profile_image") val profile_image: String,
    @SerializedName("relationship_status") val relationship_status: String,
    @SerializedName("religion") val religion: String,
    @SerializedName("smoking_habit") val smoking_habit: String,
    @SerializedName("status") val status: Int,
    @SerializedName("user_interest") val user_interest:  ArrayList<UserInterests> = arrayListOf(),
    @SerializedName("user_lat") val user_lat: String,
    @SerializedName("user_long") val user_long: String,
    @SerializedName("user_name") val user_name: String

)