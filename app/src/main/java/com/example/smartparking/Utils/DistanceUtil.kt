package com.example.smartparking.Utils

import com.example.smartparking.Data.Institutes
import com.example.smartparking.Models.Institute

class DistanceUtil {
    companion object {
        fun haversine(latStart: Double, lngStart: Double, latEnd: Double, lngEnd: Double): Double {
            val latStartRad = Math.toRadians(latStart)
            val latEndRad = Math.toRadians(latEnd)
            val deltaLat = Math.toRadians(latEnd - latStart)
            val deltaLng = Math.toRadians(lngEnd - lngStart)
            val R = 6371 * 1000

            val a = Math.pow(Math.sin(deltaLat/2), 2.0) + Math.cos(latStartRad) * Math.cos(latEndRad) * Math.pow(Math.sin(deltaLng/2), 2.0)
            val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt((1 - a)))
            val d = R * c

            // Return distance in meters
            return d
        }

        fun getNearInstitutes(target: Int): MutableList<Institute> {

            val listInstitutes = Institutes.getInstitutesList()
            var listNearInstitutes: MutableList<Institute> = mutableListOf()
            val minimumDistance = 300.0

            for (i in 0 until listInstitutes.size) {
                var distance = haversine(
                    listInstitutes.get(target).getLatitude(),
                    listInstitutes.get(target).getLongitude(),
                    listInstitutes.get(i).getLatitude(),
                    listInstitutes.get(i).getLongitude()
                )
                if (distance <= minimumDistance) {
                    listInstitutes.get(i).setDistance(distance)
                    listNearInstitutes.add(listInstitutes.get(i))
                }
            }

            return listNearInstitutes
        }
    }
}