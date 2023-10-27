package com.fajar.submissionstoryapp.core.data.resource.remote.auth

data class AuthResponse(

    val error: Boolean,
    val message: String,
    val loginResult: User
)