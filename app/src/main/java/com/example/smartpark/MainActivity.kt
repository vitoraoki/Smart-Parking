package com.example.smartpark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadSpinnerInstitutes()
        setListeners()
    }

    // Set all listeners
    private fun setListeners() {
        sendButton.setOnClickListener(this)
    }

    // Load all values to put in the Spinner that show all institutes
    private fun loadSpinnerInstitutes() {
        spinnerDynamic.adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item,
            Institutes.getInstitutesList())
    }

    // Deal with all the button click events
    override fun onClick(v: View) {
        val id = v.id

        if (id == R.id.sendButton) {
            institute.text = spinnerDynamic.selectedItem.toString()
        }
    }
}
