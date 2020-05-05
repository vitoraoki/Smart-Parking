package com.example.smartpark.Views

import android.app.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.smartpark.Data.Institutes
import com.example.smartpark.Utils.EventsSetter
import com.example.smartpark.R
import kotlinx.android.synthetic.main.activity_single_event.*
import java.text.SimpleDateFormat
import java.util.*

class SingleEvent : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private var date = ""
    private var time = ""
    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    private val simpleHourFormat = SimpleDateFormat("HH:mm")

    private lateinit var event: EventsSetter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_event)

        event = EventsSetter(this)

        loadSpinnerInstitutes()
        setListeners()
    }

    // Set all listeners
    private fun setListeners() {
        sendButton.setOnClickListener(this)
        datePicker.setOnClickListener(this)
        timePicker.setOnClickListener(this)
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
            val eventId = (System.currentTimeMillis() and 0xfffffff).toInt()

            Log.d("position", spinnerDynamic.selectedItemPosition.toString())

            event.insertEventInDataBase(eventId.toString(),
                spinnerDynamic.selectedItemPosition.toString(),
                spinnerDynamic.selectedItem.toString(), date, time, 0)
            Toast.makeText(this, "Evento salvo", Toast.LENGTH_SHORT).show()
        }
        // Deal with the click of the button "Selecione uma data"
        else if (id == R.id.datePicker) {
            openDatePickerDialog()
        }
        // Deal with the click of the button "Selecione uma hora"
        else if (id == R.id.timePicker) {
            openTimePickerDialog()
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
        val minute = calendar.get(Calendar.MINUTE) + 1

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
}
