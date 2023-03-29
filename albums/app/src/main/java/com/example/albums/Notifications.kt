package com.example.albums

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class Notifications : AppCompatActivity() {
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationManager: NotificationManager
    lateinit var builder: Notification.Builder
    private val channelId = "12345"
    private val description = "Test Notification"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
    }
    @RequiresApi(Build.VERSION_CODES.S)
    fun btnNotify(view: View) {
        val intent = Intent(this, LauncherActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        notificationChannel = NotificationChannel(channelId, description, NotificationManager .IMPORTANCE_HIGH)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.enableVibration(true)
        notificationManager.createNotificationChannel(notificationChannel)

        builder = Notification.Builder(this, channelId)
            .setContentTitle("NOTIFICATION USING " + "Hey Welcome")
            .setContentText("You Have a notifications")
            .setSmallIcon(R.drawable.speak)
            .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
            .setContentIntent(pendingIntent)
       // Log.d("notesd", builder.build().smallIcon.toString())
        notificationManager.notify(12345, builder.build())
    }
}