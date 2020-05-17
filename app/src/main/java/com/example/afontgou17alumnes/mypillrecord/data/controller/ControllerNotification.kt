package com.example.afontgou17alumnes.mypillrecord.data.controller

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.ui.login.LoginActivity

class ControllerNotification{
    companion object {

        private lateinit var context: Context

        fun setContext(con: Context) {
            context=con
            //Create notification channel
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "MyPillRecord"
                val descriptionText = "MyPillRecord"
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel("MyPillRecord", name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    getSystemService(context,NotificationManager::class.java) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
    val intent = Intent(context, LoginActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
    var builder = NotificationCompat.Builder(context, "MyPillRecord")
        .setSmallIcon(R.drawable.pill_logo)
        .setContentTitle("lin 23")
        .setContentText("lin 24")
        .setStyle(NotificationCompat.BigTextStyle()
            .bigText("lin 26"))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    






}