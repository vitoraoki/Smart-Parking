package com.example.smartpark.Views

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.smartpark.Broadcast.NotificationReceiver
import com.example.smartpark.Data.DatabaseHandler
import com.example.smartpark.Data.NotificationListAdapter
import com.example.smartpark.R
import kotlinx.android.synthetic.main.activity_list_notifications.*
import kotlinx.android.synthetic.main.delete_notification_dialog.*

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

        // Open a dialog when click on notification item, to confirm its delete or not
        notificationsList.setOnItemClickListener { parent, view, position, id ->
            val notification = notificationAdapter.getItem(position)
            if (notification != null) {
                notificationItemClick(notification.getNotificationId())
            }
        }
    }

    // Delete an notification selected by the user from the list of notifications
    private fun notificationItemClick(notificationId: String) {

        // Inflate the dialog with the layout created for the dialog box
        val dialogView = LayoutInflater.from(this).inflate(R.layout.delete_notification_dialog, null)

        // Build the alert dialog
        val alertDialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)

        //Show the dialog
        val alertDialog = alertDialogBuilder.show()

        // Deal with confirmDelete button to delete the notification
        alertDialog.confirmDelete.setOnClickListener {
            deleteNotification(notificationId)

            // Dismiss the alert dialog
            alertDialog.dismiss()
        }

        // Deal with dismissDelete button to not delete the notification
        alertDialog.dismissDelete.setOnClickListener {
            // Dismiss the alert dialog
            alertDialog.dismiss()
        }
    }

    // Delete the notification from alert manager and from database
    private fun deleteNotification(notificationId: String) {

        // Delete the notification from alert manager
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(this, notificationId.toInt(), intent, 0)
        alarmManager.cancel(pendingIntent)

        // Delete the notification from database
        val dbHelper = DatabaseHandler(this)
        val result = dbHelper.deleteNotification(notificationId)

        if(result == -1) {
            Toast.makeText(this, "Erro ao deletar a notificação", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Notificação deletada com sucesso", Toast.LENGTH_SHORT).show()
        }

        // Reload the list of notifications
        showAllNotifications()
    }
}


