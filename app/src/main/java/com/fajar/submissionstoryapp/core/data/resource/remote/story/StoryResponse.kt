package com.fajar.submissionstoryapp.core.data.resource.remote.story

data class StoryResponse(

    val listStory: List<Story>,
    val error: Boolean,
    val message: String
)