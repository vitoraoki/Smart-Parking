package com.example.smartpark.Broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.smartpark.Data.DatabaseHandler
import com.example.smartpark.Data.Institutes
import com.example.smartpark.Utils.DistanceUtil
import com.example.smartpark.R
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class NotificationReceiver : BroadcastReceiver() {

    lateinit var notificationManager : NotificationManagerCompat
    lateinit var builder : NotificationCompat.Builder
    private val channelId = "i.apps.notifications"

    private var numberInstitutes = 0
    private var parkingLotsNumberPerInstitute = JSONObject()
    private val listInstitutes = Institutes.getInstitutesList()

    private fun buildNotification(context: Context, intent: Intent, text: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(intent.getStringExtra("spinnerIndex") + ": " + intent.getStringExtra("institute"))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        } else {
            builder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(intent.getStringExtra("spinnerIndex") + ": " + intent.getStringExtra("institute"))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        }

        notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(intent.getIntExtra("id", 0), builder.build())
    }

    private fun getDataFromKonker(institute: Int, target: Int, context: Context, intent: Intent) {

        // URL to make request and user and password to authenticate
        val url = ""
        val user = ""
        val password = ""

        // Create the credentials to basic authenticate
        val credential = Credentials.basic(username = user, password = password)

        // Create the request
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", credential)
            .build()

        // Create the client that will execute the request, and execute it
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val text = "Erro ao obter os dados de vagas nos estacionamentos"
                deleteNotificationFromDataBase(context, intent.getIntExtra("id", 0).toString())
                buildNotification(context, intent, text)
            }

            override fun onResponse(call: Call, response: Response) {

                // Get the response as string and as the response come as a list, remove the brackets
                var responseStr = response.body!!.string()
                responseStr = responseStr.substring(startIndex = 1, endIndex = responseStr.length - 1)

                // Transform the response in a json
                val jsonResponse = JSONObject(responseStr)

                // Call the function that handle with the success request
                successRequestHandler(institute, jsonResponse.getJSONObject("data"), target, context, intent)
                client.dispatcher.executorService.shutdown()
            }
        })
    }

    private fun successRequestHandler(institute: Int, response: JSONObject, target: Int,
                                      context: Context, intent: Intent) {

        // Count the number of institutes that has already been verified
        numberInstitutes--

        // Save the number of parking lots per institute
        if(response.getString(institute.toString()) != "0") {
            parkingLotsNumberPerInstitute.put(institute.toString(), response.getString(institute.toString()))
        }

        // When all the institute has been verified, create the notification
        if(numberInstitutes == 0) {
            nearestInstituteHasParkingLots(target = target, context = context, intent = intent)
        }
    }

    // Get the nearest institute that has parking lots to create the notification
    private fun nearestInstituteHasParkingLots(target: Int, context: Context, intent: Intent) {

        val keys = parkingLotsNumberPerInstitute.keys()
        var text: String

        // Verify if there are no parking lots
        if(keys.hasNext() == false) {
            text = "Nenhum estacionamento próximo com vagas"
        } else {
            var nearestInstitute = -1
            var smallestDistance = -1.0

            // Iterate over all the institutes which has parking lots to discover the nearest
            while (keys.hasNext()) {
                val key = keys.next()

                var distance = DistanceUtil.haversine(
                    listInstitutes.get(target).latitude,
                    listInstitutes.get(target).longitude,
                    listInstitutes.get(key.toInt()).latitude,
                    listInstitutes.get(key.toInt()).longitude
                )

                // Get the nearest institute
                // If it is the first institute verified
                if (nearestInstitute == -1) {
                    nearestInstitute = key.toInt()
                    smallestDistance = distance
                } else {
                    // Verify if the actual distance is smaller than the previous saved
                    if (distance < smallestDistance) {
                        nearestInstitute = key.toInt()
                        smallestDistance = distance
                    }
                }
            }

            text = listInstitutes.get(nearestInstitute).instituteName

            buildNotification(context, intent, text)
            deleteNotificationFromDataBase(context, intent.getIntExtra("id", 0).toString())
        }
    }

    override fun onReceive(context: Context, intent: Intent) {

        // Get the list of near institutes
        val listNearInstitutes = DistanceUtil
            .getNearInstitutes(intent.getStringExtra("spinnerIndex").toInt())

        // Set the number of near institutes
        numberInstitutes = listNearInstitutes.size

        // For all near institute, get the number of parking lots
        for(i in listNearInstitutes) {
            getDataFromKonker(i, intent.getStringExtra("spinnerIndex").toInt(), context, intent)
        }

    }

    private fun deleteNotificationFromDataBase(context: Context, notificationId: String) {
        val dbHelper = DatabaseHandler(context)
        dbHelper.deleteNotification(notificationId)
    }

}
