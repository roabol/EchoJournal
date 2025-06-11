package br.com.sigmaonline.echojournal.echos.presentation.echos.models

import br.com.sigmaonline.echojournal.R
import br.com.sigmaonline.echojournal.core.presentation.util.UiText

data class MoodChipContent(
    val iconsRes: List<Int> = emptyList(),
    val title: UiText = UiText.StringResource(R.string.all_moods)
)
