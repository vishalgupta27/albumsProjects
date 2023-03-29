package com.example.retroproject.activity


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.retroproject.R
import com.example.retroproject.adapter.Profilesdetailsadapter
import com.example.retroproject.adapter.UserInterestsAdapter
import com.example.retroproject.model.Data
import com.example.retroproject.model.UserInterests
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson


class Profilesdetials2 : AppCompatActivity() {

    var gender: TextView? = null
    var complicated: TextView? = null
    private var location: TextView? = null
    var about: TextView? = null
    var viewpage1: ViewPager? = null
    var profilesdetailsadapter: Profilesdetailsadapter? = null
    var meet_profile_image: ArrayList<String> = ArrayList()
    var names: ArrayList<String> = ArrayList()
    var contacts: ArrayList<String> = ArrayList()

    private var adapters: UserInterestsAdapter? = null
    private var userinterests: ArrayList<UserInterests> = ArrayList()
    private var back: TextView? = null

    @SuppressLint(
        "MissingInflatedId", "SetTextI18n", "UseCompatLoadingForDrawables",
        "NotifyDataSetChanged"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profilesdetials2)

        gender = findViewById(R.id.tv_gender)
        complicated = findViewById(R.id.tv_complicated)
        location = findViewById(R.id.tv_loaction)
        viewpage1 = findViewById(R.id.viewpage1)
        about = findViewById(R.id.tv_aboutshow)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        back = findViewById(R.id.imgMenu)
        val recyclerview2 = findViewById<RecyclerView>(R.id.recyclerview2)


        val linearLayoutManager = FlexboxLayoutManager(this)
        recyclerview2?.layoutManager = linearLayoutManager

        adapters = UserInterestsAdapter(userinterests, this)
        recyclerview2.adapter = adapters


        val gson = Gson()
        val itemobj = intent.getStringExtra("profile_data")
        if (itemobj != null) {
            val profile_data = gson.fromJson(itemobj, Data::class.java)

            gender!!.text = profile_data.gender
            complicated!!.text = profile_data.relationship_status
            location!!.text = profile_data.city
            about!!.text = profile_data.about_me

            Log.d("dashboard_data", "onCreate:${profile_data.user_name}")
            for (i in 0 until profile_data.meet_profile_image.size) {
                Log.d("dashboard_data", "onCreate:${profile_data.meet_profile_image.get(i)}")
                meet_profile_image.add((profile_data.meet_profile_image.get(i)))
                names.addAll(listOf(profile_data.user_name))
                contacts.addAll(listOf(profile_data.contact_no))
                Log.d("TAG", "images>>$meet_profile_image")
            }
            userinterests.clear()
            userinterests.addAll(profile_data.user_interest)
            adapters!!.notifyDataSetChanged()

            profilesdetailsadapter = Profilesdetailsadapter(this, meet_profile_image, names, contacts )
            Log.d("TAG", "images>>check")
            viewpage1?.adapter = profilesdetailsadapter
            adapters!!.notifyDataSetChanged()

            tabLayout.setupWithViewPager(viewpage1)

            back!!.setOnClickListener {
                val intent = Intent(this, Chatpage::class.java)
                startActivity(intent)
            }

        }
    }
}
