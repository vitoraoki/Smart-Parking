package com.example.smartpark.Views

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.smartpark.Authorization.AccessTokenAuthenticator
import com.example.smartpark.Authorization.AuthorizationInterceptor
import com.example.smartpark.Authorization.AuthorizationRepository
import com.example.smartpark.Data.Institutes
import com.example.smartpark.R
import com.example.smartpark.Utils.DistanceUtil
import com.example.smartpark.Utils.NearInstitutesListAdapter
import kotlinx.android.synthetic.main.activity_list_events.*
import kotlinx.android.synthetic.main.activity_near_institutes_parking_lots.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class NearInstitutesParkingLots : AppCompatActivity() {

    val listInstitutes = Institutes.getInstitutesList()
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_near_institutes_parking_lots)

        getDataFromKonker()
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
                val text = "Erro ao obter os dados de vagas nos estacionamentos"
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
            val nearInstitutesAdapter = NearInstitutesListAdapter(
                this,
                R.layout.near_institute_row,
                listNearInstitutes,
                institutesParkingLots
            )
            nearInstitutesList.adapter = nearInstitutesAdapter

            // Show information and dismiss the progress bar
            layoutProgressBar.visibility = View.GONE
            layoutNearInstitutesList.visibility = View.VISIBLE
        }
    }
}
