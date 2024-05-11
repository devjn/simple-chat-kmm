package com.jfayz.testchat.data

import KotlinProject.composeApp.BuildConfig
import com.jfayz.domain.dao.ProfileDao
import com.jfayz.domain.model.Profile
import com.jfayz.domain.model.toProfileId
import com.jfayz.domain.usecase.CreateAiProfileUseCase
import com.jfayz.domain.usecase.GenerateAiResponseUseCase
import com.jfayz.testchat.ai.GeminiChatApi
import dev.shreyaspatil.ai.client.generativeai.GenerativeModel
import getIODispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

// Simple Service Locator
object Provider {
    val dataScope = CoroutineScope(Dispatchers.Default)
    val sameScope = CoroutineScope(Dispatchers.Unconfined)
    val dispatcherIO by lazy { getIODispatcher() }

    private val messageDao by lazy { InMemoryMessageDao() }
    val chatRepo get() = ChatRepositoryImpl(messageDao)

    val profileDao: ProfileDao by lazy { InMemoryProfileDao() }
    fun getCreateAiProfileUseCase() = CreateAiProfileUseCase(profileDao)

    // --- Gemini ---
    // Sample Profile key
    var apiKey: String = BuildConfig.API_KEY

    fun getAiResponseUseCase(profile: Profile): GenerateAiResponseUseCase {
        val model = GenerativeModel(
            modelName = "gemini-pro", // TODO: Use profile
            apiKey = profile.apiKey
        )
        return GenerateAiResponseUseCase(GeminiChatApi(model), chatRepo, dispatcherIO)
    }

    fun getAiProfile() = Profile.ai(name = "Gemini", uid = 100L, apiKey = apiKey, aiType = "Gemini")

//    val dataProvider: DataProvider by lazy { DataProvider(realm) }
}
