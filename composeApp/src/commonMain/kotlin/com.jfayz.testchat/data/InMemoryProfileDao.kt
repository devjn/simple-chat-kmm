package com.jfayz.testchat.data

import androidx.compose.runtime.mutableStateListOf
import com.jfayz.domain.dao.ProfileDao
import com.jfayz.domain.model.Profile
import com.jfayz.testchat.data.Provider.getAiProfile

class InMemoryProfileDao : ProfileDao {
    private val profiles = mutableListOf<Profile>(getAiProfile())

    override fun createProfile(profile: Profile) {
        println("Profile created: $profile")
        profiles.add(profile)
    }

    override fun getProfiles(): List<Profile> {
        return profiles.toList()
    }
}
