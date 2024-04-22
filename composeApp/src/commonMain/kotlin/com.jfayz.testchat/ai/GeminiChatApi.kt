package com.jfayz.testchat.ai

import com.jfayz.domain.repo.AiRepository
import dev.shreyaspatil.ai.client.generativeai.GenerativeModel

class GeminiChatApi(
    private val generativeModel : GenerativeModel
) : AiRepository {

    private val chat by lazy { generativeModel.startChat() }

    override suspend fun generateResponse(prompt: String): String? {
        return kotlin.runCatching {
            chat.sendMessage(prompt).text
        }.getOrElse { "<Error>: ${it.message}" }
    }
}
