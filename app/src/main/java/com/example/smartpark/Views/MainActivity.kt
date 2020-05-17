package com.example.smartpark.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.smartpark.R
import kotlinx.android.synthetic.main.activity_main.*
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButtonsListeners()
        sharedPreferences = this.getSharedPreferences("access-tokens", 0)
    }

    private fun setButtonsListeners() {
        singleEvent.setOnClickListener(this)
        repetitiveEvents.setOnClickListener(this)
        listEvents.setOnClickListener(this)
        instituteConsult.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val id = view.id

        // Go to single event activity
        if (id == R.id.singleEvent) {
            val intent = Intent(this, SingleEvent::class.java)
            startActivity(intent)
        }
        // Go to repetitive event activity
        else if (id == R.id.repetitiveEvents) {
            val intent = Intent(this, RepetitiveEvent::class.java)
            startActivity(intent)
        }
        // Go to list of events stored
        else if (id == R.id.listEvents) {
            val intent = Intent(this, ListEvents::class.java)
            startActivity(intent)
        }
        // Consult institute data
        else if (id == R.id.instituteConsult) {
            val intent = Intent(this, ConsultInstitute::class.java)
            startActivity(intent)
        }
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
                .inflate(R.layout.info_main_activity, null)

            // Build the alert dialog
            val alertDialogBuilder = AlertDialog.Builder(this)
                .setView(dialogView)

            //Show the dialog
            alertDialogBuilder.show()
        }
        return super.onOptionsItemSelected(item)
    }

}
