package com.example.albums


import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder



class BackgroundSoundService : Service() {

    var player: MediaPlayer? = null
   override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    override  fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this,R.raw.musics)
        player!!.isLooping = true // Set looping
        player!!.setVolume(100f, 100f)
    }

    @SuppressLint("WrongConstant")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player!!.start()
        return 1
    }

    override fun onStart(intent: Intent?, startId: Int) {
        // TO DO
    }

    fun onUnBind(arg0: Intent?): IBinder? {
        // TO DO Auto-generated method
        return null
    }

    fun onStop() {}
    fun onPause() {}
    override  fun onDestroy() {
        player!!.stop()
        player!!.release()
    }

    override fun onLowMemory() {}

    companion object {
        private val TAG: String? = null
    }
}