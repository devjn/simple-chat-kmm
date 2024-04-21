package com.jfayz.testchat.data

import Platform
import com.jfayz.domain.model.Profile
import com.jfayz.domain.model.toProfileId
import com.jfayz.domain.usecase.GenerateAiResponseUseCase
import com.jfayz.testchat.ai.GeminiApi
import getIODispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

// Simple Service Locator
object Provider {
    val dataScope = CoroutineScope(Dispatchers.Default)
    val sameScope = CoroutineScope(Dispatchers.Unconfined)
    val dispatcherIO by lazy { getIODispatcher() }

    val chatRepo by lazy { ChatRepositoryImpl(InMemoryMessageDao()) }
    val getAiResponseUseCase: GenerateAiResponseUseCase
        get() = GenerateAiResponseUseCase(GeminiApi(), chatRepo, dispatcherIO)

    fun getAiProfile() = Profile.ai(name = "Gemini", uid = 100L.toProfileId())


//    val dataProvider: DataProvider by lazy { DataProvider(realm) }
}
