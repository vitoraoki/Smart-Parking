package com.example.smartpark.Models

import android.content.Context
import android.content.SharedPreferences
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class AuthorizationRepository {

    private var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor

    constructor(context: Context) {
        sharedPreferences = context.getSharedPreferences("access-tokens", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    // Get the access token stored
    fun getAccessToken(): String {
        return sharedPreferences.getString("token", "token").toString()
    }

    // Get a new access token
    fun refreshAccessToken(): String {

        val url = "https://api.demo.konkerlabs.net/v1/oauth/token"
        val user = ""
        val password = ""
        val credential = Credentials.basic(username = user, password = password)

        val body = FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build()

        val request = Request.Builder()
                .addHeader("Authorization", credential)
                .url(url)
                .post(body)
                .build()

        val client = OkHttpClient()

        var newToken = ""
        client.newCall(request).execute().use { response ->
            if(!response.isSuccessful) throw IOException("Unexpected code $response")
            newToken = JSONObject(response.body!!.string())
                    .get("access_token")
                    .toString()
        }

        editor.putString("token", newToken)
        editor.commit()

        return newToken
    }
}