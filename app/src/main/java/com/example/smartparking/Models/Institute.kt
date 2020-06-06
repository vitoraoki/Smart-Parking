package com.example.smartparking.Models

class Institute {

    private var instituteId = ""
    private var instituteName = ""
    private var latitude = 0.0
    private var longitude = 0.0
    private var street = ""
    private var number = ""
    private var postCode = ""
    private var guid = ""
    private var distance = 0.0 // Distance calculated to a target institute

    constructor()
    constructor(instituteId: String, instituteName: String, latitude: Double, longitude: Double,
                street: String, number: String, postCode: String, guid: String) {
        this.instituteId = instituteId
        this.instituteName = instituteName
        this.latitude = latitude
        this.longitude = longitude
        this.street = street
        this.number = number
        this.postCode = postCode
        this.guid = guid
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

    fun getStreet(): String {
        return this.street
    }

    fun setStreet(street: String) {
        this.street = street
    }

    fun getNumber(): String {
        return this.number
    }

    fun setNumber(number: String) {
        this.number = number
    }

    fun getPostCode(): String {
        return this.postCode
    }

    fun setPostCode(postCode: String) {
        this.postCode = postCode
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

    fun getGuid(): String {
        return this.guid
    }

    fun setGuid(guid: String) {
        this.guid = guid
    }

    fun getDistance(): Double {
        return this.distance
    }

    fun setDistance(distance: Double) {
        this.distance = distance
    }
}