@file:OptIn(FlowPreview::class)

package com.plcoding.echojournal.echos.presentation.create_echo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.plcoding.echojournal.app.navigation.NavigationRoute
import com.plcoding.echojournal.core.presentation.designsystem.dropdowns.Selectable.Companion.asUnselectedItems
import com.plcoding.echojournal.echos.domain.audio.AudioPlayer
import com.plcoding.echojournal.echos.domain.recording.RecordingStorage
import com.plcoding.echojournal.echos.presentation.echos.models.PlaybackState
import com.plcoding.echojournal.echos.presentation.echos.models.TrackSizeInfo
import com.plcoding.echojournal.echos.presentation.models.MoodUi
import com.plcoding.echojournal.echos.presentation.util.AmplitudeNormalizer
import com.plcoding.echojournal.echos.presentation.util.toRecordingDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration

class CreateEchoViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val recordingStorage: RecordingStorage,
    private val audioPlayer: AudioPlayer
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val route = savedStateHandle.toRoute<NavigationRoute.CreateEcho>()
    private val recordingDetails = route.toRecordingDetails()

    private val eventChannel = Channel<CreateEchoEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(CreateEchoState(
        playbackTotalDuration = recordingDetails.duration
    ))
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeAddTopicText()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CreateEchoState()
        )

    private var durationJob: Job? = null

    fun onAction(action: CreateEchoAction) {
        when (action) {
            is CreateEchoAction.OnAddTopicTextChange -> onAddTopicTextChange(action.text)
            CreateEchoAction.OnConfirmMood -> onConfirmMood()
            CreateEchoAction.OnDismissMoodSelector -> onDismissMoodSelector()
            CreateEchoAction.OnDismissTopicSuggestions -> onDismissTopicSuggestions()
            is CreateEchoAction.OnMoodClick -> onMoodClick(action.moodUi)
            is CreateEchoAction.OnNoteTextChange -> {}
            CreateEchoAction.OnPauseAudioClick -> audioPlayer.pause()
            CreateEchoAction.OnPlayAudioClick -> onPlayAudioClick()
            is CreateEchoAction.OnRemoveTopicClick -> onRemoveTopicClick(action.topic)
            CreateEchoAction .OnSaveClick -> onSaveClick()
            is CreateEchoAction.OnTitleTextChange -> onTitleTextChange(action.text)
            is CreateEchoAction.OnTopicClick -> onTopicClick(action.topic)
            is CreateEchoAction.OnTrackSizeAvailable -> onTrackSizeAvailable(action.trackSizeInfo)
            CreateEchoAction.OnSelectMoodClick -> onSelectMoodClick()
            CreateEchoAction.OnDismissConfirmLeaveDialog -> onDismissConfirmLeaveDialog()
            CreateEchoAction.OnCancelClick,
            CreateEchoAction.OnNavigateBackClick,
            CreateEchoAction.OnGoBack -> onShowConfirmLeaveDialog()
        }
    }

    private fun onPlayAudioClick() {
        if (state.value.playbackState == PlaybackState.PAUSED) {
            audioPlayer.resume()
        } else {
            audioPlayer.play(
                filePath = recordingDetails.filePath ?: throw IllegalStateException(
                    "File path can't be null"
                ),
                onComplete = {
                    _state.update {
                        it.copy(
                            playbackState = PlaybackState.STOPPED,
                            durationPlayed = Duration.ZERO
                        )
                    }
                }
            )

            durationJob = audioPlayer
                .activeTrack
                .filterNotNull()
                .onEach { track ->
                    _state.update {
                        it.copy(
                            playbackState = if (track.isPlaying) PlaybackState.PLAYING else PlaybackState.PAUSED,
                            durationPlayed = track.durationPlayed
                        )
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun onTrackSizeAvailable(trackSizeInfo: TrackSizeInfo) {
        viewModelScope.launch(Dispatchers.Default) {
            val finalAmplitudes = AmplitudeNormalizer.normalize(
                sourceAmplitudes = recordingDetails.amplitudes,
                trackWidth = trackSizeInfo.trackWidth,
                barWidth = trackSizeInfo.barWidth,
                spacing = trackSizeInfo.spacing
            )

            _state.update {
                it.copy(
                    playbackAmplitudes = finalAmplitudes
                )
            }
        }
    }

    private fun onTitleTextChange(text: String) {
        _state.update {
            it.copy(
                titleText = text
            )
        }
    }

    private fun onSaveClick() {
        if (recordingDetails.filePath == null) {
            return
        }

        viewModelScope.launch {
            val savedFilePath = recordingStorage.savePersistently(
                tempFilePath = recordingDetails.filePath
            )
            if (savedFilePath == null) {
                eventChannel.send(CreateEchoEvent.FailedToSaveFile)
                return@launch
            }

            // TODO: Echo
        }
    }

    private fun onShowConfirmLeaveDialog() {
        _state.update {
            it.copy(
                showConfirmLeaveDialog = true
            )
        }
    }

    private fun onDismissConfirmLeaveDialog() {
        _state.update {
            it.copy(
                showConfirmLeaveDialog = false
            )
        }
    }

    private fun observeAddTopicText() {
        state
            .map { it.addTopicText }
            .distinctUntilChanged()
            .debounce(300)
            .onEach { query ->
                _state.update {
                    it.copy(
                        showTopicSuggestions = query.isNotBlank() && query.trim() !in it.topics,
                        searchResults = listOf(
                            "hello",
                            "helloworld",
                        ).asUnselectedItems()
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun onDismissTopicSuggestions() {
        _state.update {
            it.copy(
                showTopicSuggestions = false
            )
        }
    }

    private fun onRemoveTopicClick(topic: String) {
        _state.update {
            it.copy(
                topics = it.topics - topic
            )
        }
    }

    private fun onTopicClick(topic: String) {
        _state.update {
            it.copy(
                addTopicText = "",
                topics = (it.topics + topic).distinct()
            )
        }
    }

    private fun onAddTopicTextChange(text: String) {
        _state.update {
            it.copy(
                addTopicText = text.filter {
                    it.isLetterOrDigit()
                }
            )
        }
    }

    private fun onConfirmMood() {
        _state.update {
            it.copy(
                mood = it.selectedMood,
                canSaveEcho = it.titleText.isNotBlank(),
                showMoodSelector = false
            )
        }
    }

    private fun onDismissMoodSelector() {
        _state.update {
            it.copy(
                showMoodSelector = false
            )
        }
    }

    private fun onSelectMoodClick() {
        _state.update {
            it.copy(
                showMoodSelector = true
            )
        }
    }

    private fun onMoodClick(mood: MoodUi) {
        _state.update {
            it.copy(
                selectedMood = mood
            )
        }
    }
}