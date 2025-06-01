package com.plcoding.echojournal.echos.domain.recording

import kotlin.time.Duration

data class RecordingDetails(
    val duration: Duration = Duration.ZERO,
    val amplitudes: List<Float> = emptyList(),
    val filePath: String? = null
)
