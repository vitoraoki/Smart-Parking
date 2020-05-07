package com.example.smartpark.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.smartpark.Data.DatabaseHandler
import com.example.smartpark.Adapters.EventListAdapter
import com.example.smartpark.Models.Event
import com.example.smartpark.R
import com.example.smartpark.Utils.EventsUtil
import kotlinx.android.synthetic.main.access_event_dialog.*
import kotlinx.android.synthetic.main.activity_list_events.*
import kotlinx.android.synthetic.main.delete_event_dialog.*

class ListEvents : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_events)

        setListeners()
    }

    override fun onStart() {
        super.onStart()
        showAllEvents(0)
    }

    private fun setListeners() {
        btnToggleSingle.setOnClickListener(this)
        btnToggleRepetitive.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val id = view.id

        // Show the single events
        if (id == R.id.btnToggleSingle) {
            showAllEvents(0)
            singleEventList.visibility = View.GONE
            repetitiveEventList.visibility = View.VISIBLE
            btnToggleSingle.visibility = View.GONE
            btnToggleRepetitive.visibility = View.VISIBLE
            titleListSingle.visibility = View.VISIBLE
            titleListRepetitive.visibility = View.GONE

        }
        // Show the repetitive events
        else if (id == R.id.btnToggleRepetitive) {
            showAllEvents(1)
            singleEventList.visibility = View.VISIBLE
            repetitiveEventList.visibility = View.GONE
            btnToggleSingle.visibility = View.VISIBLE
            btnToggleRepetitive.visibility = View.GONE
            titleListSingle.visibility = View.GONE
            titleListRepetitive.visibility = View.VISIBLE
        }
    }

    // Get all the events stored and show in a list
    private fun showAllEvents(repetitive: Int) {
        // Get all the events stored
        val dbHelper = DatabaseHandler(this)
        var events = dbHelper.readAllEvents(repetitive)

        // Inflate the listView with all the events stored
        val eventAdapter = EventListAdapter(
            this,
            R.layout.event_row,
            events
        )
        eventsList.adapter = eventAdapter

        eventsList.setOnItemClickListener { parent, view, position, id ->
            val event = eventAdapter.getItem(position)
            if (event != null) {
                this.eventItemClick(event)
            }
        }

        // Open a dialog when long click on event item, to confirm its delete or not
        eventsList.setOnItemLongClickListener { parent, view, position, id ->
            val event = eventAdapter.getItem(position)
            if (event != null) {
                this.eventItemLongClick(event.getEventId())
            }
            true
        }

    }

    // Deal with the click in an event in list view
    private fun eventItemClick(event: Event) {

        // Inflate the dialog with the layout created for the dialog box
        val dialogView = LayoutInflater.from(this).inflate(R.layout.access_event_dialog, null)

        // Build the alert dialog
        val alertDialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)

        //Show the dialog
        val alertDialog = alertDialogBuilder.show()

        // Deal with confirmAccess button to access the event
        alertDialog.confirmAccess.setOnClickListener {
            val intent = Intent(this, NearInstitutesParkingLots::class.java)
            intent.putExtra("instituteId", event.getInstituteId())
            startActivity(intent)

            // If the event is accessed it is deleted
            val eventsUtil = EventsUtil(this)
            eventsUtil.deleteEventFromDatabase(event.getEventId())

            // Dismiss the alert dialog
            alertDialog.dismiss()
        }

        // Deal with dismissAccess button to not access the notification
        alertDialog.dismissAccess.setOnClickListener {
            // Dismiss the alert dialog
            alertDialog.dismiss()
        }
    }

    // Delete an event selected by the user from the list of events
    private fun eventItemLongClick(eventId: String) {

        // Inflate the dialog with the layout created for the dialog box
        val dialogView = LayoutInflater.from(this).inflate(R.layout.delete_event_dialog, null)

        // Build the alert dialog
        val alertDialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)

        //Show the dialog
        val alertDialog = alertDialogBuilder.show()

        // Deal with confirmDelete button to delete the event
        alertDialog.confirmDelete.setOnClickListener {
            val eventsUtil = EventsUtil(this)
            val result = eventsUtil.deleteEventFromDatabase(eventId)

            if(result == -1) {
                Toast.makeText(this, "Erro ao deletar o evento", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Evento deletado com sucesso", Toast.LENGTH_SHORT).show()
            }

            // Dismiss the alert dialog
            alertDialog.dismiss()
        }

        // Deal with dismissDelete button to not delete the notification
        alertDialog.dismissDelete.setOnClickListener {
            // Dismiss the alert dialog
            alertDialog.dismiss()
        }
    }
}


