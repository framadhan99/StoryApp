package com.fajar.submissionstoryapp.core.data.resource.remote.story

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class StorySource @Inject constructor(
    private val storyServices: StoryServices,
    private val storyDatabase: StoryDatabase
) {

    fun loadData(token: String): Flow<PagingData<Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = RemoteMediator(storyDatabase, storyServices, token),
            pagingSourceFactory = {
//                StoryPagingSource(storyServices, token)
                storyDatabase.StoryDao().getAllStory()
            }
        ).flow
    }

    suspend fun getData(): List<Story> {
        return storyDatabase.StoryDao().getMapAll()
    }

    suspend fun addStory(
        token: String,
        desc: String,
        img: MultipartBody.Part
    ): Flow<ApiResult<StoryResponse>> {
        return flow {
            try {
                emit(ApiResult.loading())
                val response = storyServices.addStory(
                    BearerToken = "Bearer $token",
                    desc.toRequestBody("text/plain".toMediaType()),
                    img
                )
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
}