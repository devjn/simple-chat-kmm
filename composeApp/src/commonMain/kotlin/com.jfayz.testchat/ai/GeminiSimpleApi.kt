package com.jfayz.testchat.ai

import com.jfayz.domain.repo.AiRepository
import dev.shreyaspatil.ai.client.generativeai.GenerativeModel

class GeminiSimpleApi(
    private val generativeModel : GenerativeModel
) : AiRepository{

    override suspend fun generateResponse(prompt: String): String? {
//        val inputContent = content() {
//            text(prompt)
//        }
        // TODO: Add error handling
        return generativeModel.generateContent(prompt).text
    }
}
