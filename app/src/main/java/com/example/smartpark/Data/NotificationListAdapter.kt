package com.example.smartpark.Data

import android.annotation.SuppressLint
import com.example.smartpark.Models.Notification
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridLayout
import android.widget.TextView
import com.example.smartpark.R
import com.example.smartpark.Views.ListNotifications
import kotlinx.android.synthetic.main.notification_row.view.*

class NotificationListAdapter (
    var mContext: Context,
    var resource: Int,
    var notifications: MutableList<Notification>) : ArrayAdapter<Notification>(mContext, resource, notifications) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val view = layoutInflater.inflate(resource, null)

        val instituteNameView = view.findViewById<TextView>(R.id.instituteName)
        val dateAndTimeView = view.findViewById<TextView>(R.id.dateAndTime)

        var notification = notifications.get(position)
        instituteNameView.text = notification.getInstituteName()
        dateAndTimeView.text = ""
        dateAndTimeView.append(notification.getDate() + " " + notification.getTime())

        return view
    }
}