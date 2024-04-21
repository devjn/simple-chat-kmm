package com.jfayz.domain.usecase

import com.jfayz.domain.model.Profile
import com.jfayz.domain.repo.AiRepository
import com.jfayz.domain.repo.ChatRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GenerateAiResponseUseCase(
    private val aiRepository: AiRepository,
    private val chatRepo: ChatRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(
        fromProfile: Profile, toProfile: Profile, prompt: String
    ): String? = withContext(ioDispatcher) {
        return@withContext aiRepository.generateResponse(prompt)?.also {
            chatRepo.sendMessage(it, fromProfile.uid, toProfile.uid)
        }
    }
}
