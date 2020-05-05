package com.example.smartpark.Models

class Institute {

    private var instituteId = ""
    private var instituteName = ""
    private var latitude = 0.0
    private var longitude = 0.0
    private var distance = 0.0 // Distance calculated to a target institute

    constructor()
    constructor(instituteId: String, instituteName: String, latitude: Double, longitude: Double) {
        this.instituteId = instituteId
        this.instituteName = instituteName
        this.latitude = latitude
        this.longitude = longitude
    }

    fun getInstituteId(): String {
        return this.instituteId
    }

    fun setInstituteId(instituteId: String) {
        this.instituteId = instituteId
    }

    fun getInstituteName(): String {
        return this.instituteName
    }

    fun setInstituteName(instituteName: String) {
        this.instituteName = instituteName
    }

    fun getLatitude(): Double {
        return this.latitude
    }

    fun setLatitude(latitude: Double) {
        this.latitude = latitude
    }

    fun getLongitude(): Double {
        return this.longitude
    }

    fun setLongitude(longitude: Double) {
        this.longitude = longitude
    }

    fun getDistance(): Double {
        return this.distance
    }

    fun setDistance(distance: Double) {
        this.distance = distance
    }
}