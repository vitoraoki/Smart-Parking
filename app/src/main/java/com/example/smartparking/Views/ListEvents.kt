package com.example.smartparking.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smartparking.Adapters.PageAdapter
import com.example.smartparking.R
import kotlinx.android.synthetic.main.activity_list_events.*

class ListEvents : AppCompatActivity() {

    private lateinit var fragmentAdpter: PageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_events)

        // Create the fragment adapter
        fragmentAdpter = PageAdapter(supportFragmentManager)
        view_pager.adapter = fragmentAdpter

        // Set the tablayout with the viewpager
        lyt_tab.setupWithViewPager(view_pager)

        // Show the back button in toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}