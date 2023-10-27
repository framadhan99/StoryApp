package com.fajar.submissionstoryapp.core.data.resource.remote.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class AuthSource @Inject constructor(
    private val authService: AuthServices,
    private val dataStoreManager: DataStoreManager
) {


    suspend fun registerUser(authBody: AuthBody): Flow<ApiResult<AuthResponse>> {
        return flow {
            try {
                emit(ApiResult.loading())
                val response = authService.registerUser(authBody)
                if (!response.error) {
                    emit(ApiResult.success(response))
                } else {
                    emit(ApiResult.error(response.message))
                }
            } catch (ex: Exception) {
                emit(ApiResult.error(ex.message.toString()))
            }
        }
    }

    suspend fun loginUser(authBody: AuthBody): Flow<ApiResult<AuthResponse>> {
        return flow {
            try {
                emit(ApiResult.loading())
                val response = authService.loginUser(authBody)
                if (!response.error) {
                    dataStoreManager.storeSession(response.loginResult.token)
                    emit(ApiResult.success(response))
                } else {
                    emit(ApiResult.error(response.message))
                }
            } catch (ex: Exception) {
                emit(ApiResult.error(ex.message.toString()))
            }
        }
    }

}