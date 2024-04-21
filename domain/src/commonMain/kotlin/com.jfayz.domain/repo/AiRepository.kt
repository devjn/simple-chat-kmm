package com.jfayz.domain.repo

import com.jfayz.domain.model.Message

interface AiRepository {
    suspend fun generateResponse(prompt: String) : String?
}
