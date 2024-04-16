package data

import ai.GeminiProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

// Simple Service Locator
object Provider {
    val dataScope = CoroutineScope(Dispatchers.Default)
    val sameScope = CoroutineScope(Dispatchers.Unconfined)

    val chatRepo by lazy { ChatRepositoryImpl(InMemoryMessageDao()) }
    fun getAiProfile() = GeminiProfile(name = "Gemini", chatRepo)


//    val dataProvider: DataProvider by lazy { DataProvider(realm) }
}
