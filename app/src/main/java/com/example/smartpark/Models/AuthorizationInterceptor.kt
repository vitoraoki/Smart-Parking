package com.example.smartpark.Models

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val authorizationRepository: AuthorizationRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var accessToken: String

        // If there isn't any token registered, get a new one. Else, get the existed
        if (authorizationRepository.getAccessToken().equals("token")) {
            accessToken = authorizationRepository.refreshAccessToken()
        } else {
            accessToken = authorizationRepository.getAccessToken()
        }

        // Build the new request
        val newRequest = chain
            .request()
            .newBuilder()
            .header("Authorization", accessToken)
            .build()

        return chain.proceed(newRequest)
    }

}