package com.example.albums


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.albums.adapters.WeathersAdapter
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*


class WeathersAPI : AppCompatActivity() {

    private var itemList = ArrayList<InforWeat>()
    lateinit var adapter: WeathersAdapter
    var name: TextView? = null
    var showdates: TextView? = null
    var sunrise: TextView? = null
    var sunset: TextView? = null
    var lats: TextView? = null
    var long: TextView? = null
    var temp_max: TextView? = null
    var navigation_view: NavigationView? = null

    private val cameraRequest = 1888
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2


    var cityEditText: EditText? = null
    private var call: Call<InforWeat>? = null
    private lateinit var progressBar: ProgressBar

    var drawerLayout: DrawerLayout? = null
    var actionBarDrawerToggle: ActionBarDrawerToggle? = null


    var profile_image: ImageView? = null
    var upload: TextView? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weathrs_api)
        name = findViewById(R.id.tv_Weaname)
        showdates = findViewById(R.id.tv_ShowDates)
        sunrise = findViewById(R.id.tv_sunrise)
        sunset = findViewById(R.id.tv_sunset)
        long = findViewById(R.id.tv_long)
        lats = findViewById(R.id.tv_lats)
        lats = findViewById(R.id.tv_lats)
        temp_max = findViewById(R.id.temp_max)

        cityEditText = findViewById(R.id.cityEditText)
        progressBar = findViewById(R.id.progress_Bar)
        navigation_view = findViewById(R.id.NavigationView)

        val menuItem1 = navigation_view!!.menu.findItem(R.id.nav_music)
        val menuItem2 = navigation_view!!.menu.findItem(R.id.nav_login)


        menuItem1.setOnMenuItemClickListener {
            val intent = Intent(this, BackgroundSoundService::class.java)
            startService(intent)
            true
        }

        menuItem2.setOnMenuItemClickListener {
            Log.e("TAG", "onCreate:87 Check",)
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            true
        }

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout!!.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val headerView = navigation_view!!.getHeaderView(0)
        val usernameText = headerView.findViewById<TextView>(R.id.username_text)


        upload = findViewById(R.id.upload)
        profile_image = findViewById(R.id.profile_image)
        upload?.setTextColor((resources.getColor(R.color.pinkColor)))
       /* upload!!.setOnClickListener {
            Toast.makeText(this, "vlip uplaod", Toast.LENGTH_SHORT).show()
            openGallery()
        }*/
        //upload photo by gallery and take camera
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        )
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                cameraRequest
            )

      /*  profile_image!!.setOnClickListener {
            Toast.makeText(this, "vlip images", Toast.LENGTH_SHORT).show()
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, cameraRequest)
        }*/

        // Set the user's name in the TextView
        usernameText.text = "John Doe"




/*        val weatherRv = findViewById<RecyclerView>(R.id.recyclerView)
        val linearLayoutManager = LinearLayoutManager(this)
        weatherRv?.layoutManager = linearLayoutManager

        adapter = WeathersAdapter(itemList, this, object : WeathersAdapter.BtnClickListener {
            override fun onBtnClick(position: Int, type: String) {
            }
        })
        weatherRv.adapter = adapter*/


        // Add a text change listener to the EditText
        cityEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Not used
                progressBar.visibility = View.INVISIBLE
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Make the API request and update the UI with the retrieved data
                progressBar.visibility = View.VISIBLE
                // Cancel the previous API request if it's still running
                call?.cancel()

                // Check if the user has entered any text
                if (s.isNotBlank()) {

                    getWeather()
                } else {
                    // Clear the UI data
                    progressBar.visibility = View.INVISIBLE
                    sunrise?.text = ""
                    sunset?.text = ""
                    lats?.text = ""
                    long?.text = ""
                    temp_max?.text = ""
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Not used
            }
        })

        // getWeather()
    }


    // for convert timestamp to date and time
    private fun getTime(s: Long): String? {
        try {
            val sdf = SimpleDateFormat(" hh:mm aa")
            val netDate = Date(java.lang.Long.valueOf(s) * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    private fun getDate(s: Long): String? {
        try {
            val sdf = SimpleDateFormat(" dd/MM/yyyy ")
            val netDate = Date(java.lang.Long.valueOf(s) * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    private fun convertKelvinToFahrenheit(temperatureInKelvin: Double?): Double? {
        return if (temperatureInKelvin != null) {
            // Convert Kelvin to Fahrenheit using the formula (K − 273.15) × 9/5 + 32
            //  (temperatureInKelvin - 273.15) * 9/5 + 32
            // Convert Kelvin to celcius
            (temperatureInKelvin - 273.15)
        } else {
            null
        }
    }

    private fun getWeather() {
        val chat = Methods.methodsinstance.openWeather(
            cityEditText?.text.toString(),
            "3950dee4428eb6f2dcd3ebf07f8c73bb"
        )
        chat.enqueue(object : Callback<Map<Any, Any>?> {
            @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation", "SetTextI18n")
            override fun onResponse(
                call: Call<Map<Any, Any>?>, response: Response<Map<Any, Any>?>
            ) {
                progressBar.visibility = View.GONE
                val chat = response.body()
                if (chat != null) {
                    Log.d("getchat57", chat.toString())
                    val gson = Gson()
                    val objrespose = gson.fromJson(gson.toJson(chat), InforWeat::class.java)
                    val temperatureInFahrenheit =
                        convertKelvinToFahrenheit(objrespose.list[0].main.tempMax)?.let {
                            BigDecimal(it).setScale(2, RoundingMode.HALF_UP).toInt()
                        }
                    name?.text = "Name :  " + objrespose.city.name
                    showdates?.text = "Date :  " + getDate(objrespose.city.sunrise)
                    sunrise?.text = "Sunrise :  " + getTime(objrespose.city.sunrise)
                    sunset?.text = "Sunset :  " + getTime(objrespose.city.sunset)
                    long?.text = "Longitude :  " + objrespose.city.coord?.lon.toString()
                    lats?.text = "Latitude :  " + objrespose.city.coord?.lat.toString()
                    //  temp_max?.text = "Max_Temperature : " + objrespose.list[0].main.tempMax  + " \u2109"
                    temp_max?.text = "Max_Temperature : $temperatureInFahrenheit \u2103"

                    Log.d("getchat134", "onResponse:${objrespose.city.name} ")
                    Log.d("getchat135", "onResponse:${objrespose.list} ")
                }
            }

            override fun onFailure(call: Call<Map<Any, Any>?>, t: Throwable) {
                Log.i("errorMessage", t.message!!)
                progressBar.visibility = View.GONE
            }
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequest) {
            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            profile_image!!.setImageBitmap(photo)
        } else if (requestCode == REQUEST_PICK_IMAGE) {
            val uri = data?.data
            profile_image!!.setImageURI(uri)
        }
    }

    //for gallery
    fun openGallery() {
        Intent(Intent.ACTION_GET_CONTENT).also { intent ->
            intent.type = "image/*"
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_PICK_IMAGE)

            }
        }
    }
}