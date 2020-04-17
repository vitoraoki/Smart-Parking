package com.example.smartpark.Data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.smartpark.Models.Notification

val DATABASE_NAME = "SmartPark"
val TABLE_NAME = "Notifications"
val COL_ID = "id"
val COL_NOTIFICATION_ID = "notificationId"
val COL_INSTITUTE_NAME = "instituteName"
val COL_DATE = "date"
val COL_TIME = "time"

class DatabaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " +
                TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NOTIFICATION_ID + " VARCHAR(256)," +
                COL_INSTITUTE_NAME + " VARCHAR(256)," +
                COL_DATE + " VARCHAR(256)," +
                COL_TIME + " VARCHAR(256))"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    // Insert the new notification to the database
    fun insertNotification(notification: Notification): Long {
        val writeDB = this.writableDatabase
        var cv = ContentValues()

        cv.put(COL_NOTIFICATION_ID, notification.getNotificationId())
        cv.put(COL_INSTITUTE_NAME, notification.getInstituteName())
        cv.put(COL_DATE, notification.getDate())
        cv.put(COL_TIME, notification.getTime())

        var result = writeDB.insert(TABLE_NAME, null, cv)

        writeDB.close()
        return result
    }

    // Read all the notifications that are stored in database
    fun readAllNotification() :MutableList<Notification> {
        var notifications : MutableList<Notification> = ArrayList()

        // Query all the notifications
        val readDB = this.readableDatabase
        val query = "SELECT * FROM " + TABLE_NAME
        val result = readDB.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                var notification = Notification()
                notification.setId(result.getString(result.getColumnIndex(COL_ID)).toInt())
                notification.setNotificationId(result.getString(result.getColumnIndex(
                    COL_NOTIFICATION_ID)))
                notification.setInstituteName(result.getString(result.getColumnIndex(
                    COL_INSTITUTE_NAME)))
                notification.setDate(result.getString(result.getColumnIndex(
                    COL_DATE)))
                notification.setTime(result.getString(result.getColumnIndex(
                    COL_TIME)))
                notifications.add(notification)
            } while (result.moveToNext())
        }

        readDB.close()
        result.close()
        return notifications
    }

    // Delete a notification from database
    fun deleteNotification(notificationId: String): Int {
        val writeDB = this.writableDatabase

        // Query to delete a notification based on its notification id
        val queryDelete = COL_NOTIFICATION_ID + " = " + notificationId
        val result = writeDB.delete(TABLE_NAME, queryDelete, null)

        writeDB.close()
        return result
    }

}