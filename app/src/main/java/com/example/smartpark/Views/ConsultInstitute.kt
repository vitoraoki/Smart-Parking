package com.example.smartpark.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.smartpark.Data.Institutes
import com.example.smartpark.R
import kotlinx.android.synthetic.main.activity_consult_institute.*

class ConsultInstitute : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consult_institute)

        this.loadSpinnerInstitutes()
        this.setListeners()
    }

    // Load all values to put in the Spinner that show all institutes
    private fun loadSpinnerInstitutes() {
        spinnerInstitutes.adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item,
            Institutes.getInstituteNamesList())
    }

    // Set the listeners from the view
    private fun setListeners() {
        selectedInstitute.setOnClickListener(this)
        nearInstitutesMap.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val id = view.id

        // Deal with the button to select institute to consult
        if (id == R.id.selectedInstitute) {
            Toast.makeText(this, spinnerInstitutes.selectedItem.toString(), Toast.LENGTH_SHORT).show()
        }
        // Deal with the button to open a map with near institutes
        else if (id == R.id.nearInstitutesMap) {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("activityParent", "0") // 0 : ConsultInstitute
//            intent.putExtra("targetInstituteId", spinnerInstitutes.selectedItemPosition.toString())
            intent.putExtra("targetInstituteId", "13")
            startActivity(intent)
        }
    }


}
