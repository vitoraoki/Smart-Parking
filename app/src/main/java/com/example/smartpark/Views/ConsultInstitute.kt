package com.example.smartpark.Views

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.smartpark.Authorization.AccessTokenAuthenticator
import com.example.smartpark.Authorization.AuthorizationInterceptor
import com.example.smartpark.Authorization.AuthorizationRepository
import com.example.smartpark.Data.Institutes
import com.example.smartpark.R
import com.google.android.gms.common.server.response.FastJsonResponse
import kotlinx.android.synthetic.main.activity_consult_institute.*
import kotlinx.android.synthetic.main.info_maps_dialog.view.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ConsultInstitute : AppCompatActivity(), View.OnClickListener {

    private var instituteId = ""
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consult_institute)

        this.loadSpinnerInstitutes()
        this.setListeners()

        // Show the back button in toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

    // Deal with the clicks buttons
    override fun onClick(view: View) {
        val id = view.id

        // Deal with the button to select institute to consult
        if (id == R.id.selectedInstitute) {
            instituteId = spinnerInstitutes.selectedItemPosition.toString()

            // Change the visibility of the layouts to show the progress bar and make the button
            // to see the map, not clickable
            errorRequestConsult.visibility = View.GONE
            layoutProgressBarConsult.visibility = View.VISIBLE
            layoutNameAndLogo.visibility = View.GONE
            layoutData.visibility = View.GONE
            nearInstitutesMap.isClickable = false

            // Get the data from konker to show the number of parking lots
            this.getDataFromKonker()
        }
        // Deal with the button to open a map with near institutes
        else if (id == R.id.nearInstitutesMap) {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("activityParent", "0") // 0 : ConsultInstitute
            intent.putExtra("targetInstituteId", instituteId)
            startActivity(intent)
        }
    }

    private fun getDataFromKonker() {

        // Initialize a shared preferences to get the token to access api
        sharedPreferences = this.getSharedPreferences("access-tokens", 0)

        // Initialize the authenticator repository
        val authorizationRepository = AuthorizationRepository(this)

        val guid = "2d948131-137e-47ed-8699-0d2a6b387a7f"
        val application = "yv0ntjaj"
        val channel = "parkinglots"
        val url = "https://api.demo.konkerlabs.net/v1/$application/incomingEvents?q=device:$guid channel:$channel &sort=newest&limit=1"

        val request = Request.Builder()
            .url(url)
            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor(authorizationRepository))
            .authenticator(AccessTokenAuthenticator(authorizationRepository))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

                // If an error occur, ask to reload the page
                runOnUiThread {
                    errorRequestConsult.visibility = View.VISIBLE
                    layoutProgressBarConsult.visibility = View.GONE
                    layoutNameAndLogo.visibility = View.GONE
                    layoutData.visibility = View.GONE
                    nearInstitutesMap.isClickable = false
                }
            }

            override fun onResponse(call: Call, response: Response) {

                // Get the response as string
                var responseStr = response.body!!.string()

                // Transform the response in a json
                val jsonResponse = JSONObject(responseStr)
                    .getJSONArray("result")
                    .getJSONObject(0)
                    .getJSONObject("payload")

                // Call the function that handle with the success request
                showInstituteData(jsonResponse)
                client.dispatcher.executorService.shutdown()
            }
        })
    }

    // After get data from konker, show this data
    private fun showInstituteData(jsonResponse: JSONObject) {

        // Get the institute
        val institute = Institutes.getInstitutesList().get(instituteId.toInt())

        // Set the institute data information
        val logoId = resources.getIdentifier("logo" + instituteId,
            "drawable", packageName)
        instituteLogo.setImageResource(logoId)
        instituteInitials.text = institute.getInstituteName().split(" - ").get(0)
        instituteNameConsult.text = institute.getInstituteName().split(" - ").get(1)
        instituteStreet.text = institute.getStreet()
        instituteNumber.text = institute.getNumber()
        institutePostCode.text = institute.getPostCode()
        instNumPL.text = jsonResponse.get(instituteId).toString()

        // Change the visibility of the layouts to show the data and make the button to see the map
        // clickable
        runOnUiThread {
            errorRequestConsult.visibility = View.GONE
            layoutProgressBarConsult.visibility = View.GONE
            layoutNameAndLogo.visibility = View.VISIBLE
            layoutData.visibility = View.VISIBLE
            nearInstitutesMap.isClickable = true
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
                .inflate(R.layout.info_consult_institute, null)

            // Build the alert dialog
            val alertDialogBuilder = AlertDialog.Builder(this)
                .setView(dialogView)

            //Show the dialog
            alertDialogBuilder.show()
        }
        return super.onOptionsItemSelected(item)
    }

}
