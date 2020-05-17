package com.example.afontgou17alumnes.mypillrecord.notificacions2.reciver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.afontgou17alumnes.mypillrecord.R
import com.example.afontgou17alumnes.mypillrecord.notificacions2.sendNotification

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // TODO: Step 1.10 [Optional] remove toast
//        Toast.makeText(context, context.getText(R.string.eggs_ready), Toast.LENGTH_SHORT).show()

        // TODO: Step 1.9 add call to sendNotification
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotification("linia 24 reciver",
            context
        )

    }

}