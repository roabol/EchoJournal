package br.com.sigmaonline.echojournal.echos.presentation.echos

import br.com.sigmaonline.echojournal.echos.domain.echo.Echo
import br.com.sigmaonline.echojournal.echos.presentation.echos.models.PlaybackState
import br.com.sigmaonline.echojournal.echos.presentation.models.EchoUi
import br.com.sigmaonline.echojournal.echos.presentation.models.MoodUi
import kotlin.time.Duration

fun Echo.toEchoUi(
    currentPlaybackDuration: Duration = Duration.ZERO,
    playbackState: PlaybackState = PlaybackState.STOPPED
): EchoUi {
    return EchoUi(
        id = id!!,
        title = title,
        mood = MoodUi.valueOf(mood.name),
        recordedAt = recordedAt,
        note = note,
        topics = topics,
        amplitudes = audioAmplitudes,
        playbackTotalDuration = audioPlaybackLength,
        audioFilePath = audioFilePath,
        playbackCurrentDuration = currentPlaybackDuration,
        playbackState = playbackState
    )
}