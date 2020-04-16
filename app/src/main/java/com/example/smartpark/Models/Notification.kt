package com.example.smartpark.Models

class Notification {

    private var id = 0
    private var notificationId = ""
    private var instituteName = ""
    private var date = ""
    private var time = ""

    fun getId(): Int {
        return this.id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getNotificationId(): String {
        return this.notificationId
    }

    fun setNotificationId(notificationId: String) {
        this.notificationId = notificationId
    }

    fun getInstituteName(): String {
        return this.instituteName
    }

    fun setInstituteName(instituteName: String) {
        this.instituteName = instituteName
    }

    fun getDate(): String {
        return this.date
    }

    fun setDate(date: String) {
        this.date = date
    }

    fun getTime(): String {
        return this.time
    }

    fun setTime(time: String) {
        this.time = time
    }
}