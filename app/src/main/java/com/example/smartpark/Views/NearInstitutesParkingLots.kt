package com.example.smartpark.Views

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.smartpark.Authorization.AccessTokenAuthenticator
import com.example.smartpark.Authorization.AuthorizationInterceptor
import com.example.smartpark.Authorization.AuthorizationRepository
import com.example.smartpark.R
import com.example.smartpark.Utils.DistanceUtil
import com.example.smartpark.Adapters.NearInstitutesListAdapter
import com.example.smartpark.Utils.EventsUtil
import kotlinx.android.synthetic.main.activity_near_institutes_parking_lots.*
import kotlinx.android.synthetic.main.delete_event_dialog.*
import kotlinx.android.synthetic.main.delete_event_dialog.view.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.concurrent.schedule

class NearInstitutesParkingLots : AppCompatActivity(), View.OnClickListener {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_near_institutes_parking_lots)

        setListeners()

        // Show the back button in toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        getDataFromKonker()
    }

    override fun onPause() {
        super.onPause()
        if (this::timer.isInitialized) {
            timer.cancel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::timer.isInitialized) {
            timer.cancel()
        }
    }

    private fun setListeners() {
        reloadButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val id = view.id

        // If the reload button is clicked, reload the page
        if (id == R.id.reloadButton) {

            // Show progress bar and dismiss information
            layoutProgressBar.visibility = View.VISIBLE
            layoutNearInstitutesList.visibility = View.GONE
            reloadButton.visibility = View.GONE
            errorRequest.visibility = View.GONE

            getDataFromKonker()
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
                    errorRequest.visibility = View.VISIBLE
                    reloadButton.visibility = View.VISIBLE
                    layoutProgressBar.visibility = View.GONE
                    layoutNearInstitutesList.visibility = View.GONE
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
                showNearInstitutes(jsonResponse)
                client.dispatcher.executorService.shutdown()
            }
        })
    }

    private fun showNearInstitutes(institutesParkingLots: JSONObject) {

        // Get the list of near institutes to the target institute
        val listNearInstitutes = DistanceUtil.getNearInstitutes(
            intent.getStringExtra("instituteId").toInt())

        // Sort the list of near institutes by it's distances
        listNearInstitutes.sortBy { it.getDistance() }

        // Set the number of parking lots for the near institutes
        runOnUiThread {

            // Set the list of near institutes with the name of institute name and the number of
            // parking lots

            // Inflate the listView with all the near institutes
            val nearInstitutesAdapter =
                NearInstitutesListAdapter(
                    this,
                    R.layout.near_institute_row,
                    listNearInstitutes,
                    institutesParkingLots
                )
            nearInstitutesList.adapter = nearInstitutesAdapter

            nearInstitutesList.setOnItemClickListener { parent, view, position, id ->
                val institute = nearInstitutesAdapter.getItem(position)
                if (institute != null) {
                    val intent = Intent(this, MapsActivity::class.java)
                    intent.putExtra("activityParent", "1") // 0 : NearInstitutesParkingLots
                    intent.putExtra("targetInstituteId", this.intent.getStringExtra("instituteId"))
                    intent.putExtra("nearInstituteId", institute.getInstituteId())
                    startActivity(intent)
                }
            }

            // Show information and dismiss the progress bar
            layoutProgressBar.visibility = View.GONE
            layoutNearInstitutesList.visibility = View.VISIBLE
            reloadButton.visibility = View.VISIBLE
            errorRequest.visibility = View.GONE
        }
        setTimer()
    }

    // Set the timer that will reload the page after 1 minute
    private fun setTimer() {

        // Always cancel the previous timer
        if (this::timer.isInitialized) {
            timer.cancel()
        }

        timer = Timer()
        timer.schedule(10000) {
            getDataFromKonker()
        }
    }
}
