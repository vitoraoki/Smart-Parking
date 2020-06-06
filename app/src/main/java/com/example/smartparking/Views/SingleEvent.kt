package com.example.smartparking.Views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.smartparking.Data.Institutes
import com.example.smartparking.R
import com.example.smartparking.Utils.EventsUtil
import kotlinx.android.synthetic.main.activity_single_event.*
import java.text.SimpleDateFormat
import java.util.*

class SingleEvent : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private var date = ""
    private var time = ""
    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    private val simpleHourFormat = SimpleDateFormat("HH:mm")

    private lateinit var eventsUtil: EventsUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_event)

        eventsUtil = EventsUtil(this)

        this.loadSpinnerInstitutes()
        this.setListeners()

        // Show the back button in toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Function to hide keyboard
    fun hideKeyboard(v: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    // Load all values to put in the Spinner that show all institutes
    private fun loadSpinnerInstitutes() {
        spinnerInstSingleEvent.adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item,
            Institutes.getInstituteNamesList())
    }

    // Set all listeners
    private fun setListeners() {
        sendButtonSngEvent.setOnClickListener(this)
        datePickerSngEvent.setOnClickListener(this)
        timePickerSngEvent.setOnClickListener(this)

        // Hide keyboard when spinner is touched
        spinnerInstSingleEvent.setOnTouchListener { v, event ->
            this.hideKeyboard(v)
            false
        }

        // Hide keyboard when layout is touched
        sngEventLayout.setOnTouchListener { v, event ->
            this.hideKeyboard(v)
            true
        }
    }

    // Deal with all the button click events
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(view: View) {
        val id = view.id
        this.hideKeyboard(view)

        // Deal with the click of the button "Enviar"
        if (id == R.id.sendButtonSngEvent) {
            val eventId = (System.currentTimeMillis() and 0xfffffff).toInt()

            // Verify if all the fields are filled
            if (!sngEventTitle.text.isEmpty() && !date.isEmpty() && !time.isEmpty()) {
                eventsUtil.insertEventInDataBase(
                    eventId.toString(),
                    sngEventTitle.text.toString(),
                    spinnerInstSingleEvent.selectedItemPosition.toString(),
                    spinnerInstSingleEvent.selectedItem.toString(),
                    date,
                    time,
                    0)
                Toast.makeText(this, "Evento salvo", Toast.LENGTH_SHORT).show()
            } else {

                var textError = "Favor completar os campos: "

                if (sngEventTitle.text.isEmpty()) {
                    textError += "título, "
                }
                if (date.isEmpty()) {
                    textError += "data, "
                }
                if (time.isEmpty()) {
                    textError += "horário, "
                }

                textError = textError.substring(0, textError.length - 2)
                Toast.makeText(this, textError, Toast.LENGTH_LONG).show()
            }

        }
        // Deal with the click of the button "Selecione uma data"
        else if (id == R.id.datePickerSngEvent) {
            this.openDatePickerDialog()
        }
        // Deal with the click of the button "Selecione uma hora"
        else if (id == R.id.timePickerSngEvent) {
            this.openTimePickerDialog()
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
        datePickerSngEvent.text = date
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
        timePickerSngEvent.text = time
    }

    // Show info button on top bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.info_menu, menu)
        return true
    }

    // Deal with the click on the info button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.info_menu) {
            // Inflate the dialog with the layout created for the dialog box
            val dialogView = LayoutInflater
                .from(this)
                .inflate(R.layout.info_single_event, null)

            // Build the alert dialog
            val alertDialogBuilder = AlertDialog.Builder(this)
                .setView(dialogView)

            //Show the dialog
            alertDialogBuilder.show()
        }
        return super.onOptionsItemSelected(item)
    }
}
