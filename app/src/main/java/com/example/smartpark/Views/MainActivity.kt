package com.example.smartpark.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.smartpark.R
import kotlinx.android.synthetic.main.activity_main.*
import android.content.SharedPreferences

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

}
