package com.example.smartparking.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.smartparking.Adapters.EventListAdapter
import com.example.smartparking.Data.DatabaseHandler
import com.example.smartparking.Models.Event
import com.example.smartparking.R
import com.example.smartparking.Utils.EventsUtil
import com.example.smartparking.Views.NearInstitutesParkingLots
import kotlinx.android.synthetic.main.access_event_dialog.*
import kotlinx.android.synthetic.main.activity_list_events_old.*
import kotlinx.android.synthetic.main.delete_event_dialog.*
import kotlinx.android.synthetic.main.fragment_r_events_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [REventsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class REventsListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val repetitive = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_r_events_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        showAllEvents(repetitive)
    }

    // Get all the events stored and show in a list
    private fun showAllEvents(repetitive: Int) {
        // Get all the events stored
        val dbHelper = DatabaseHandler(requireContext())
        var events = dbHelper.readAllEvents(repetitive)

        // Inflate the listView with all the events stored
        val eventAdapter = EventListAdapter(
            requireContext(),
            R.layout.event_row,
            events
        )
        repEventsList.adapter = eventAdapter

        repEventsList.setOnItemClickListener { parent, view, position, id ->
            val event = eventAdapter.getItem(position)
            if (event != null) {
                goToNearInstPLActivity(event)
            }
        }

        // Open a dialog when long click on event item, to confirm its delete or not
        repEventsList.setOnItemLongClickListener { parent, view, position, id ->
            val event = eventAdapter.getItem(position)
            if (event != null) {
                eventItemLongClick(event.getEventId())
            }
            true
        }

    }

    // Go to the activity that will show the number of parking lots per near institutes
    private fun goToNearInstPLActivity(event: Event) {
        val intent = Intent(requireActivity(), NearInstitutesParkingLots::class.java)
        intent.putExtra("instituteId", event.getInstituteId())
        startActivity(intent)
    }

    // Delete an event selected by the user from the list of events
    private fun eventItemLongClick(eventId: String) {

        // Build the alert dialog
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle("Deseja apagar o evento?")

        // Deal with confirm button to delete the event
        alertDialogBuilder.setPositiveButton("Sim") { dialog, which ->
            val eventsUtil = EventsUtil(requireContext())
            val result = eventsUtil.deleteEventFromDatabase(eventId)

            if(result == -1) {
                Toast.makeText(requireContext(), "Erro ao deletar o evento", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Evento deletado com sucesso", Toast.LENGTH_SHORT).show()
            }

            showAllEvents(repetitive)
        }

        // Deal with dismiss button to not delete the event
        alertDialogBuilder.setNegativeButton("Não") { dialog, which ->  }

        //Show the dialog and change the colors of the buttons
        val alertDialog = alertDialogBuilder.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.positive))
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.negative))
    }
}