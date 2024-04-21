package com.jfayz.testchat.ai

import KotlinProject.composeApp.BuildConfig
import com.jfayz.domain.model.Message
import com.jfayz.domain.repo.AiRepository
import dev.shreyaspatil.ai.client.generativeai.GenerativeModel

class GeminiApi : AiRepository{
    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.API_KEY
    )

    override suspend fun generateResponse(prompt: String): String? {
//        val inputContent = content() {
//            text(prompt)
//        }
        // TODO: Add error handling
        return generativeModel.generateContent(prompt).text
    }
}
