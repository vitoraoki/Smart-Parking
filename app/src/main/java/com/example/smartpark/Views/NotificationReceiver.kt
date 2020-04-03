package com.example.smartpark.Views

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.smartpark.Data.Institutes
import com.example.smartpark.Distances.DistanceUtil
import com.example.smartpark.R
import kotlinx.android.synthetic.main.activity_main.*

class NotificationReceiver : BroadcastReceiver() {

    lateinit var notificationManager : NotificationManagerCompat
    lateinit var builder : NotificationCompat.Builder
    private val channelId = "i.apps.notifications"

    override fun onReceive(context: Context, intent: Intent) {

        val listInstitutes = Institutes.getInstitutesList()
        val listNearInstitutes = DistanceUtil.calculateDistances(intent.getStringExtra("spinnerIndex").toInt())
        var text = ""

        listNearInstitutes.forEach {
            text += listInstitutes.get(it).instituteName + ", "
            println(listInstitutes.get(it).instituteName)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(intent.getStringExtra("spinnerIndex") + ": " + intent.getStringExtra("institute"))
//                .setContentText(intent.getStringExtra("dateAndTime"))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        } else {
            builder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(intent.getStringExtra("spinnerIndex") + ": " + intent.getStringExtra("institute"))
//                .setContentText(intent.getStringExtra("dateAndTime"))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        }

        notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(intent.getIntExtra("id", 0), builder.build())
    }

}