package com.example.smartpark.Views

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.smartpark.Broadcast.NotificationReceiver
import com.example.smartpark.Data.DatabaseHandler
import com.example.smartpark.Data.Institutes
import com.example.smartpark.Models.Notification
import com.example.smartpark.R
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private var date = ""
    private var time = ""
    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    private val simpleHourFormat = SimpleDateFormat("HH:mm")

    lateinit var alarmManager : AlarmManager
    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel : NotificationChannel
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()
        loadSpinnerInstitutes()
        setListeners()
    }

    // Set all listeners
    private fun setListeners() {
        sendButton.setOnClickListener(this)
        datePicker.setOnClickListener(this)
        timePicker.setOnClickListener(this)
        goToListNotifications.setOnClickListener(this)
    }

    // Load all values to put in the Spinner that show all institutes
    private fun loadSpinnerInstitutes() {
        spinnerDynamic.adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item,
            Institutes.getInstituteNamesList())
    }

    // Deal with all the button click events
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(view: View) {
        val id = view.id

        // Deal with the click of the button "Enviar"
        if (id == R.id.sendButton) {
            instituteSelected.text = spinnerDynamic.selectedItem.toString()
            dateSelected.text = date
            timeSelected.text = time

            // Set a notification alarm to the date and hour given
            setNotification()
        }
        // Deal with the click of the button "Selecione uma data"
        else if (id == R.id.datePicker) {
            openDatePickerDialog()
        }
        // Deal with the click of the button "Selecione uma hora"
        else if (id == R.id.timePicker) {
            openTimePickerDialog()
        }
        // Deal with the click of the button "Cancelar Alarme"
        else if (id == R.id.goToListNotifications) {
            val intent = Intent(this, ListNotifications::class.java)
            startActivity(intent)
        }
    }

    // Open the dialog with the calendar to pick a date
    private fun openDatePickerDialog() {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, this, year, month, day).show()
    }

    // Deal with the date that are selected
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        date = simpleDateFormat.format(calendar.time)
        datePicker.text = date
    }

    // Open the dialog with the calendar to pick a time
    private fun openTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(this, this, hourOfDay, minute, true).show()
    }

    // Deal with the time that are selected
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.set(year, month, day, hourOfDay, minute)
        time = simpleHourFormat.format(calendar.time)
        timePicker.text = time
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Create the channel for the notification
            notificationChannel = NotificationChannel(
                channelId,description,NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)

            notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNotification() {
        val intent = Intent(this, NotificationReceiver::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val notificationId = (System.currentTimeMillis() and 0xfffffff).toInt()
        intent.putExtra("id", notificationId)
        intent.putExtra("institute", spinnerDynamic.selectedItem.toString())
        intent.putExtra("dateAndTime", date + " - " + time)
        intent.putExtra("spinnerIndex", spinnerDynamic.selectedItemPosition.toString())
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(this, notificationId, intent, 0)

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        var timeAtButtonClick = System.currentTimeMillis()
        val dateTime = LocalDateTime.parse(date + " " + time,
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            .atZone(ZoneId.of("America/Sao_Paulo"))
            .toInstant()
            .toEpochMilli()

        if((dateTime - timeAtButtonClick) >= 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, dateTime, pendingIntent)
            }
            insertNotificationInDataBase(notificationId.toString(), spinnerDynamic.selectedItem.toString())
            Toast.makeText(this, "Alarme criado com sucesso", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Escolha uma data e hora posterior a atual", Toast.LENGTH_LONG).show()
        }
    }

    // Insert the new notification in database
    private fun insertNotificationInDataBase(notificationId: String, instituteName: String) {
        val dbHelper = DatabaseHandler(this)
        var notification = Notification()

        // Build the notification that will be stored in database
        notification.setNotificationId(notificationId)
        notification.setInstituteName(instituteName)
        notification.setDate(date)
        notification.setTime(time)
        dbHelper.insertNotification(notification)
    }

//    private fun cancelNotification() {
//        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
//        val intent = Intent(this, NotificationReceiver::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(this, idNotification, intent, 0)
//        alarmManager.cancel(pendingIntent)
//    }
}
