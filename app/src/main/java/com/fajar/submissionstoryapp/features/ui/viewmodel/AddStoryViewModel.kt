package com.fajar.submissionstoryapp.features.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fajar.submissionstoryapp.core.data.resource.local.store.DataStoreManager
import com.fajar.submissionstoryapp.core.data.resource.remote.ApiResult
import com.fajar.submissionstoryapp.core.data.resource.remote.story.StoryRepo
import com.fajar.submissionstoryapp.core.data.resource.remote.story.StoryResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject


class AddStoryViewModel @Inject constructor(
    private val storyRepository: StoryRepo,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val tokenUser: LiveData<String> = dataStoreManager.tokenUser.asLiveData()

    fun addStory(
        token: String,
        desc: String,
        img: MultipartBody.Part
    ): LiveData<ApiResult<StoryResponse>> {
        val result = MutableLiveData<ApiResult<StoryResponse>>()
        viewModelScope.launch {
            storyRepository.addStory(token, desc, img).collect {
                result.postValue(it)
            }
        }
        return result
    }


}