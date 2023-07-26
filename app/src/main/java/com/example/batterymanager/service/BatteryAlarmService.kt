package com.example.batterymanager.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import com.example.batterymanager.R

class BatteryAlarmService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        createNotificationChannel()
        startNotification()
        return START_STICKY
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    //lets make notification channel that is use to another project so i noted here to remember

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_MIN)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun startNotification() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("My Title")
            .setContentText("this is my content")
            .setSmallIcon(R.drawable.health_good)
            .build()


        startForeground(1, notification)
    }

    companion object {
        const val CHANNEL_ID = "BatteryManagerChannel"
        const val CHANNEL_NAME = "BatteryManagerSERVICE"
    }
}