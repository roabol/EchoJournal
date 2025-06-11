package br.com.sigmaonline.echojournal.echos.di

import br.com.sigmaonline.echojournal.echos.data.AndroidAudioPlayer
import br.com.sigmaonline.echojournal.echos.data.echo.RoomEchoDataSource
import br.com.sigmaonline.echojournal.echos.data.recording.AndroidVoiceRecorder
import br.com.sigmaonline.echojournal.echos.data.recording.InternalRecordingStorage
import br.com.sigmaonline.echojournal.echos.data.settings.DataStoreSettings
import br.com.sigmaonline.echojournal.echos.domain.audio.AudioPlayer
import br.com.sigmaonline.echojournal.echos.domain.echo.EchoDataSource
import br.com.sigmaonline.echojournal.echos.domain.recording.RecordingStorage
import br.com.sigmaonline.echojournal.echos.domain.recording.VoiceRecorder
import br.com.sigmaonline.echojournal.echos.domain.settings.SettingsPreferences
import br.com.sigmaonline.echojournal.echos.presentation.create_echo.CreateEchoViewModel
import br.com.sigmaonline.echojournal.echos.presentation.echos.EchosViewModel
import br.com.sigmaonline.echojournal.echos.presentation.settings.SettingsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


val echoModule = module {
    singleOf(::AndroidVoiceRecorder) bind VoiceRecorder::class
    singleOf(::InternalRecordingStorage) bind RecordingStorage::class
    singleOf(::AndroidAudioPlayer) bind AudioPlayer::class
    singleOf(::RoomEchoDataSource) bind EchoDataSource::class
    singleOf(::DataStoreSettings) bind SettingsPreferences::class

    viewModelOf(::EchosViewModel)
    viewModelOf(::CreateEchoViewModel)
    viewModelOf(::SettingsViewModel)
}