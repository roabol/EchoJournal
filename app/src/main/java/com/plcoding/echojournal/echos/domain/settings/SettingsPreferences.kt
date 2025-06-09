package com.plcoding.echojournal.echos.domain.settings

import com.plcoding.echojournal.echos.domain.echo.Mood
import kotlinx.coroutines.flow.Flow

interface SettingsPreferences {
    suspend fun saveDefaultTopics(topic: List<String>)
    fun observeDefaultTopics(): Flow<List<String>>

    suspend fun saveDefaultMood(mood: Mood)
    fun observeDefaultMood(): Flow<Mood>
}