package com.jfayz.domain.usecase

import com.jfayz.domain.dao.ProfileDao
import com.jfayz.domain.model.Profile
import kotlin.random.Random

class CreateAiProfileUseCase(
    private val profileDao: ProfileDao
) {
    operator fun invoke(name: String, apiKey: String, aiType: String) {
        val newProfile = Profile.ai(
            uid = Random.nextLong(from = 10, until = Long.MAX_VALUE),
            name = name,
            apiKey = apiKey,
            aiType = aiType
        )
        profileDao.createProfile(newProfile)
    }
}
