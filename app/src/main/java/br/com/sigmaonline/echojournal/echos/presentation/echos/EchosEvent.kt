package br.com.sigmaonline.echojournal.echos.presentation.echos

import br.com.sigmaonline.echojournal.echos.domain.recording.RecordingDetails

interface EchosEvent {
    data object RequestAudioPermission : EchosEvent
    data object RecordingTooShort : EchosEvent
    data class OnDoneRecording(val details: RecordingDetails) : EchosEvent
}