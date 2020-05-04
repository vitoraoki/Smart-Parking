package com.example.smartpark.Models

class Event {

    private var id = 0
    private var eventId = ""
    private var instituteId = 0
    private var instituteName = ""
    private var date = ""
    private var time = ""
    private var repetitive = 0

    fun getId(): Int {
        return this.id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getEventId(): String {
        return this.eventId
    }

    fun setEventId(eventId: String) {
        this.eventId = eventId
    }

    fun getInstituteId(): Int {
        return this.instituteId
    }

    fun setInstituteId(instituteId: Int) {
        this.instituteId = instituteId
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

    fun getRepetitive(): Int {
        return this.repetitive
    }

    fun setRepetitive(repetitive: Int) {
        this.repetitive = repetitive
    }
}