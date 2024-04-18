package com.jfayz.testchat.ai

import KotlinProject.composeApp.BuildConfig
import dev.shreyaspatil.ai.client.generativeai.GenerativeModel

class GeminiApi {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.API_KEY
    )

    suspend fun generateResponse(prompt: String): String? {
//        val inputContent = content() {
//            text(prompt)
//        }
        return generativeModel.generateContent(prompt).text
    }
}
