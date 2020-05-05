package com.example.smartpark.Data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.smartpark.Models.Event

val DATABASE_NAME = "SmartPark"
val TABLE_NAME = "Events"
val COL_ID = "id"
val COL_EVENT_ID = "eventId"
val COL_INSTITUTE_ID = "instituteId"
val COL_INSTITUTE_NAME = "instituteName"
val COL_DATE = "date"
val COL_TIME = "time"
val COL_REPETITIVE = "repetitive"

class DatabaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " +
                TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_EVENT_ID + " VARCHAR(256)," +
                COL_INSTITUTE_ID + " VARCHAR(256)," +
                COL_INSTITUTE_NAME + " VARCHAR(256)," +
                COL_DATE + " VARCHAR(256)," +
                COL_TIME + " VARCHAR(256)," +
                COL_REPETITIVE + " INTEGER)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    // Insert the new event to the database
    fun insertEvent(event: Event): Long {
        val writeDB = this.writableDatabase
        var cv = ContentValues()

        cv.put(COL_EVENT_ID, event.getEventId())
        cv.put(COL_INSTITUTE_ID, event.getInstituteId())
        cv.put(COL_INSTITUTE_NAME, event.getInstituteName())
        cv.put(COL_DATE, event.getDate())
        cv.put(COL_TIME, event.getTime())
        cv.put(COL_REPETITIVE, event.getRepetitive())

        var result = writeDB.insert(TABLE_NAME, null, cv)

        writeDB.close()
        return result
    }

    // Read all the events that are stored in database
    fun readAllEvents(repetitive: Int) :MutableList<Event> {
        var events : MutableList<Event> = ArrayList()

        // Query all the events
        val readDB = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_REPETITIVE = $repetitive"
        val result = readDB.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                var event = Event()
                event.setId(result.getString(result.getColumnIndex(COL_ID)).toInt())
                event.setEventId(result.getString(result.getColumnIndex(
                    COL_EVENT_ID)))
                event.setInstituteId(result.getString(result.getColumnIndex(COL_INSTITUTE_ID)))
                event.setInstituteName(result.getString(result.getColumnIndex(
                    COL_INSTITUTE_NAME)))
                event.setDate(result.getString(result.getColumnIndex(
                    COL_DATE)))
                event.setTime(result.getString(result.getColumnIndex(
                    COL_TIME)))
                events.add(event)
            } while (result.moveToNext())
        }

        readDB.close()
        result.close()
        return events
    }

    // Delete an event from database
    fun deleteEvent(eventId: String): Int {
        val writeDB = this.writableDatabase

        // Query to delete an event based on its notification id
        val queryDelete = COL_EVENT_ID + " = " + eventId
        val result = writeDB.delete(TABLE_NAME, queryDelete, null)

        writeDB.close()
        return result
    }

}