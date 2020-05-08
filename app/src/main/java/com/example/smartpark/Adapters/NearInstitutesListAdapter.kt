package com.example.smartpark.Adapters

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.smartpark.Models.Institute
import com.example.smartpark.R
import org.json.JSONObject

class NearInstitutesListAdapter (
    var mContext: Context,
    var resource: Int,
    var nearInstitutes: MutableList<Institute>,
    val institutesParkingLots: JSONObject) : ArrayAdapter<Institute>(mContext, resource, nearInstitutes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val view = layoutInflater.inflate(resource, null)

        val nearInstituteNameView = view.findViewById<TextView>(R.id.instituteName)
        val nearInstituteParkingLotsView = view.findViewById<TextView>(R.id.parkingLots)

        var institute = nearInstitutes.get(position)
        nearInstituteNameView.text = institute.getInstituteName()

        // Verify if it is just one parking lot
        if (institutesParkingLots.getString(
                institute.getInstituteId()) == "1") {
            nearInstituteParkingLotsView.text = institutesParkingLots.getString(
                institute.getInstituteId()) + " vaga"
        } else {
            nearInstituteParkingLotsView.text = institutesParkingLots.getString(
                institute.getInstituteId()) + " vagas"
        }

        // If the institute is the target institute, change the layout of its row
        if (position == 0) {
            nearInstituteNameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30.toFloat())
            nearInstituteParkingLotsView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40.toFloat())
        }

        // Put green when the institute parking lot has at least one parking lot
        if (institutesParkingLots.get(institute.getInstituteId()).toString().toInt() > 0) {
            nearInstituteParkingLotsView.setTextColor(ContextCompat.getColor(mContext, R.color.positive))
        } else {
            nearInstituteParkingLotsView.setTextColor(ContextCompat.getColor(mContext, R.color.negative))
        }

        return view
    }
}