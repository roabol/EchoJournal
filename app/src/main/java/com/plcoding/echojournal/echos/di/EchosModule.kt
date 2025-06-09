package com.plcoding.echojournal.echos.di

import com.plcoding.echojournal.echos.data.AndroidAudioPlayer
import com.plcoding.echojournal.echos.data.echo.RoomEchoDataSource
import com.plcoding.echojournal.echos.data.recording.AndroidVoiceRecorder
import com.plcoding.echojournal.echos.data.recording.InternalRecordingStorage
import com.plcoding.echojournal.echos.domain.audio.AudioPlayer
import com.plcoding.echojournal.echos.domain.echo.EchoDataSource
import com.plcoding.echojournal.echos.domain.recording.RecordingStorage
import com.plcoding.echojournal.echos.domain.recording.VoiceRecorder
import com.plcoding.echojournal.echos.presentation.create_echo.CreateEchoViewModel
import com.plcoding.echojournal.echos.presentation.echos.EchosViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val echoModule = module {
    singleOf(::AndroidVoiceRecorder) bind VoiceRecorder::class
    singleOf(::InternalRecordingStorage) bind RecordingStorage::class
    singleOf(::AndroidAudioPlayer) bind AudioPlayer::class
    singleOf(::RoomEchoDataSource) bind EchoDataSource::class

    viewModelOf(::EchosViewModel)
    viewModelOf(::CreateEchoViewModel)
}