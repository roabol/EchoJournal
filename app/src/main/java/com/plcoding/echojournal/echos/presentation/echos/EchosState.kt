package com.plcoding.echojournal.echos.presentation.echos

import android.adservices.topics.Topic
import com.plcoding.echojournal.R
import com.plcoding.echojournal.core.presentation.designsystem.dropdowns.Selectable
import com.plcoding.echojournal.core.presentation.designsystem.dropdowns.Selectable.Companion.asUnSelectedItems
import com.plcoding.echojournal.core.presentation.util.UiText
import com.plcoding.echojournal.echos.presentation.echos.models.EchoFilterChip
import com.plcoding.echojournal.echos.presentation.echos.models.MoodChipContent
import com.plcoding.echojournal.echos.presentation.models.MoodUi

data class EchosState(
    val hasEchosRecorded: Boolean = false,
    val hasActiveTopicFilters: Boolean = false,
    val hasActiveMoodFilters: Boolean = false,
    val isLoadingData: Boolean = false,
    val moods: List<Selectable<MoodUi>> = emptyList(),
    val topics: List<Selectable<String>> = listOf("love", "Happy", "work").asUnSelectedItems(),
    val moodChipContent: MoodChipContent = MoodChipContent(),
    val selectedEchoFilterChip: EchoFilterChip? = null,
    val topicChipTitle: UiText = UiText.StringResource(R.string.all_topics)
)