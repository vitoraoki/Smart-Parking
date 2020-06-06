package com.example.smartparking.Views

import android.app.TimePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.smartparking.Data.Institutes
import com.example.smartparking.R
import com.example.smartparking.Utils.EventsUtil
import kotlinx.android.synthetic.main.activity_repetitive_event.*
import java.text.SimpleDateFormat
import java.util.*

class RepetitiveEvent : AppCompatActivity(), View.OnClickListener,
    TimePickerDialog.OnTimeSetListener {

    private var time = ""
    private val simpleHourFormat = SimpleDateFormat("HH:mm")
    private lateinit var eventsUtil: EventsUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repetitive_event)

        eventsUtil = EventsUtil(this)

        this.loadSpinnerInstitutes()
        this.loadSpinnerDaysOfWeek()
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
        spinnerInstRepEvent.adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item,
            Institutes.getInstituteNamesList())
    }

    // Put all days of week in the spinner
    private fun loadSpinnerDaysOfWeek() {

        val daysOfWeek = listOf<String>("Segunda-feira", "Terça-feira", "Quarta-feira",
            "Quinta-feira", "Sexta-feira", "Sábado", "Domingo")

        spinnerDaysWeek.adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item,
            daysOfWeek)
    }

    // Set all listeners
    private fun setListeners() {
        sendButtonRtvEvent.setOnClickListener(this)
        timePickerRtvEvent.setOnClickListener(this)

        // Hide keyboard when spinner is touched
        spinnerInstRepEvent.setOnTouchListener { v, event ->
            this.hideKeyboard(v)
            false
        }
        spinnerDaysWeek.setOnTouchListener { v, event ->
            this.hideKeyboard(v)
            false
        }

        // Hide keyboard when layout is touched
        rtvEventLayout.setOnTouchListener { v, event ->
            this.hideKeyboard(v)
            true
        }
    }

    override fun onClick(view: View) {
        val id = view.id
        this.hideKeyboard(view)

        // Deal with the click of the button "Enviar"
        if (id == R.id.sendButtonRtvEvent) {
            val eventId = (System.currentTimeMillis() and 0xfffffff).toInt()

            // Verify if all the fields are filled
            if (!rtvEventTitle.text.isEmpty() && !time.isEmpty()) {
                eventsUtil.insertEventInDataBase(
                    eventId.toString(),
                    rtvEventTitle.text.toString(),
                    spinnerInstRepEvent.selectedItemPosition.toString(),
                    spinnerInstRepEvent.selectedItem.toString(),
                    spinnerDaysWeek.selectedItem.toString(),
                    time,
                    1)

                Toast.makeText(this, "Evento salvo", Toast.LENGTH_SHORT).show()
            } else {

                var textError = "Favor completar os campos: "

                if (rtvEventTitle.text.isEmpty()) {
                    textError += "título, "
                }
                if (time.isEmpty()) {
                    textError += "horário, "
                }

                textError = textError.substring(0, textError.length - 2)
                Toast.makeText(this, textError, Toast.LENGTH_LONG).show()
            }

        }
        // Deal with the click of the button "Selecione uma hora"
        else if (id == R.id.timePickerRtvEvent) {
            this.openTimePickerDialog()
        }
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
        timePickerRtvEvent.text = time
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
                .inflate(R.layout.info_repetitive_event, null)

            // Build the alert dialog
            val alertDialogBuilder = AlertDialog.Builder(this)
                .setView(dialogView)

            //Show the dialog
            alertDialogBuilder.show()
        }
        return super.onOptionsItemSelected(item)
    }
}
