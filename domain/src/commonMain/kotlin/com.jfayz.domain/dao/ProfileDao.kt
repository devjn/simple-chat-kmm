package com.jfayz.domain.dao

import com.jfayz.domain.model.Profile

interface ProfileDao {
   fun createProfile(profile: Profile)
//   fun getProfile(uid: String): Profile?
   fun getProfiles(): List<Profile>
}
