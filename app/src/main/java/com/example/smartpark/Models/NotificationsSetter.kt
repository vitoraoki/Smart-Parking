package com.example.smartpark.Models

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.smartpark.Broadcast.NotificationReceiver
import com.example.smartpark.Data.DatabaseHandler
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class NotificationsSetter {

    private lateinit var alarmManager : AlarmManager
    private lateinit var notificationManager : NotificationManager
    private lateinit var notificationChannel : NotificationChannel
    private var context: Context
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"

    constructor(context: Context) {
        this.context = context
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Create the channel for the notification
            notificationChannel = NotificationChannel(
                channelId,description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)

            notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setNotification(institute: String, date: String, time: String, spinnerIndex: String,
                        repetitive: Int) {

        createNotificationChannel()
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val notificationId = (System.currentTimeMillis() and 0xfffffff).toInt()
        intent.putExtra("id", notificationId)
        intent.putExtra("institute", institute)
        intent.putExtra("dateAndTime", date + " - " + time)
        intent.putExtra("spinnerIndex", spinnerIndex)
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, notificationId,
            intent, 0)

        alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

        var timeAtButtonClick = System.currentTimeMillis()
        val dateTime = LocalDateTime.parse(date + " " + time,
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            .atZone(ZoneId.of("America/Sao_Paulo"))
            .toInstant()
            .toEpochMilli()

        if((dateTime - timeAtButtonClick) >= 0) {

            // First try to add the notification in database
            val result = insertNotificationInDataBase(notificationId.toString(), institute,
                date, time, repetitive)

            // If the data was successfully added to database, create the alarm notification
            if(result != -1.toLong()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, dateTime, pendingIntent)
                    Toast.makeText(context, "Alarme criado com sucesso", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Erro ao criar o alarme", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Escolha uma data e hora posterior a atual", Toast.LENGTH_SHORT).show()
        }
    }

    // Insert the new notification in database
    private fun insertNotificationInDataBase(notificationId: String, instituteName: String,
                                             date: String, time: String, repetitive: Int): Long {
        val dbHelper = DatabaseHandler(context)
        var notification = Notification()

        // Build the notification that will be stored in database
        notification.setNotificationId(notificationId)
        notification.setInstituteName(instituteName)
        notification.setDate(date)
        notification.setTime(time)
        notification.setRepetitive(repetitive)
        return dbHelper.insertNotification(notification)
    }
}