package com.example.smartpark.Utils

import com.example.smartpark.Data.Institutes

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

            return d
        }

        fun getNearInstitutes(target: Int): MutableList<Int> {

            val listInstitutes = Institutes.getInstitutesList()
            var listNearInstitutes: MutableList<Int> = mutableListOf<Int>()
            val minimumDistance = 300.0

            for (i in 0..listInstitutes.size - 1) {
                var distance = haversine(
                    listInstitutes.get(target).latitude,
                    listInstitutes.get(target).longitude,
                    listInstitutes.get(i).latitude,
                    listInstitutes.get(i).longitude
                )
                if (distance <= minimumDistance) {
                    listNearInstitutes.add(i)
                }
            }

            return listNearInstitutes
        }


    }
}