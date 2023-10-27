package com.fajar.submissionstoryapp.core.data.resource.remote.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

@Singleton
class AuthRepo @Inject constructor(private val authDataSource: AuthSource) {

    suspend fun registerUser(authBody: AuthBody): Flow<ApiResult<AuthResponse>> {
        return authDataSource.registerUser(authBody).flowOn(Dispatchers.IO)
    }

    suspend fun loginUser(authBody: AuthBody): Flow<ApiResult<AuthResponse>> {
        return authDataSource.loginUser(authBody).flowOn(Dispatchers.IO)
    }

}