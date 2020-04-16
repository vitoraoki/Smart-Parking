package com.example.smartpark.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.smartpark.Data.DatabaseHandler
import com.example.smartpark.Data.NotificationListAdapter
import com.example.smartpark.Models.Notification
import com.example.smartpark.R
import kotlinx.android.synthetic.main.activity_list_notifications.*

class ListNotifications : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_notifications)

        showAllNotifications()
    }

    // Get all the notifications stored and show in a list
    private fun showAllNotifications() {
        // Get all the notifications stored
        val dbHelper = DatabaseHandler(this)
        var notifications = dbHelper.readAllNotification()

        // Inflate the listView with all the notifications stored
        val notificationAdapter = NotificationListAdapter(
            this,
            R.layout.notification_row,
            notifications
        )
        notificationsList.adapter = notificationAdapter

        // Set an Toast to each click on the list
        notificationsList.setOnItemClickListener { parent, view, position, id ->
            val notification = notificationAdapter.getItem(position)
            if (notification != null) {
                Toast.makeText(this, notification.getNotificationId(), Toast.LENGTH_LONG).show()
            }
        }
    }
}


