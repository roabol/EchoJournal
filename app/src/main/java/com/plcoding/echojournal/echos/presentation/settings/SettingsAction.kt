package com.plcoding.echojournal.echos.presentation.settings

import com.plcoding.echojournal.echos.presentation.models.MoodUi

sealed interface SettingsAction {
    data class OnSearchTextChange(val text: String) : SettingsAction
    data object OnCreateTopicClick : SettingsAction
    data class OnRemoveTopicClick(val topic: String) : SettingsAction
    data object OnBackClick : SettingsAction
    data object OnDismissTopicDropDown : SettingsAction
    data object OnAddButtonClick : SettingsAction
    data class  OnMoodClick(val mood: MoodUi) : SettingsAction
}