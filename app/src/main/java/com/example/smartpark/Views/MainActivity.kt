package com.example.smartpark.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.smartpark.R
import kotlinx.android.synthetic.main.activity_list_notifications.*
import kotlinx.android.synthetic.main.activity_main.*
import android.content.SharedPreferences
import android.util.Log
import com.example.smartpark.Models.AuthorizationRepository

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButtonsListeners()
        sharedPreferences = this.getSharedPreferences("access-tokens", 0)
    }

    private fun setButtonsListeners() {
        singleNotification.setOnClickListener(this)
        repetitiveNotifications.setOnClickListener(this)
        goToListNotifications.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val id = view.id

        // Go to single notification activity
        if (id == R.id.singleNotification) {
            val intent = Intent(this, SingleNotification::class.java)
            startActivity(intent)
        }
        // Go to repetitive notification activity
        else if (id == R.id.repetitiveNotifications) {
            Toast.makeText(this, "Em breve", Toast.LENGTH_SHORT).show()
        }
        // Go to list of notifications stored
        else if (id == R.id.goToListNotifications) {
            val intent = Intent(this, ListNotifications::class.java)
            startActivity(intent)
        }
    }

}
