package com.example.smartpark.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.smartpark.Data.Institutes
import com.example.smartpark.R
import com.example.smartpark.Utils.DistanceUtil
import kotlinx.android.synthetic.main.activity_near_institutes_parking_lots.*

class NearInstitutesParkingLots : AppCompatActivity() {

    val listInstitutes = Institutes.getInstitutesList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_near_institutes_parking_lots)

        showNearInstitutes(intent.getIntExtra("instituteId", 0))
    }

    private fun showNearInstitutes(institute: Int) {
        val listNearInstitutes = DistanceUtil.getNearInstitutes(institute)

        var text = ""

        for (institute in listNearInstitutes) {
            text += listInstitutes.get(institute).instituteName + "\n"
        }

        institutesList.text = text
    }
}
