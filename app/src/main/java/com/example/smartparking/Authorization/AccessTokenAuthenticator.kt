package com.example.smartparking.Authorization
import okhttp3.Authenticator
import okhttp3.Response
import okhttp3.*

import okhttp3.Route


class AccessTokenAuthenticator(private val authorizationRepository: AuthorizationRepository) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {

        // Get the actual token
        val oldToken = authorizationRepository.getAccessToken()

        synchronized(this) {
            var newToken = authorizationRepository.getAccessToken()

            // Check if the request use authentication
            if (response.request.header("Authorization") != null) {

                // Verify if the token has changed
                if (oldToken != newToken) {
                    return response.request
                            .newBuilder()
                            .removeHeader("Authorization")
                            .addHeader("Authorization", "Bearer $newToken")
                            .build()
                }

                // If the token hasn't changed get a new token
                newToken = authorizationRepository.refreshAccessToken()
                return response.request
                        .newBuilder()
                        .removeHeader("Authorization")
                        .addHeader("Authorization", "Bearer $newToken")
                        .build()
            }
        }
        return null
    }
}