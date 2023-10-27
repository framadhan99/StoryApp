package com.fajar.submissionstoryapp.core.data.resource.remote.auth

interface AuthServices {

    @POST("register")
    suspend fun registerUser(
        @Body authBody: AuthBody
    ): AuthResponse

    @POST("login")
    suspend fun loginUser(
        @Body authBody: AuthBody
    ): AuthResponse

}