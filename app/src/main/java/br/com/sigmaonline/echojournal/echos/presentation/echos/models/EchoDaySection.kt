package br.com.sigmaonline.echojournal.echos.presentation.echos.models

import br.com.sigmaonline.echojournal.core.presentation.util.UiText
import br.com.sigmaonline.echojournal.echos.presentation.models.EchoUi

data class EchoDaySection(
    val dateHeader: UiText,
    val echos: List<EchoUi>
)
