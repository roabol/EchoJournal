package br.com.sigmaonline.echojournal.echos.presentation.echos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.sigmaonline.echojournal.R
import br.com.sigmaonline.echojournal.core.presentation.designsystem.theme.EchoJournalTheme
import br.com.sigmaonline.echojournal.core.presentation.designsystem.theme.Microphone
import br.com.sigmaonline.echojournal.core.presentation.designsystem.theme.Pause

private const val PRIMARY_BUTTON_BUBBLE_SIZE_DP = 128
private const val SECONDARY_BUTTON_SIZE_DP = 48

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EchoRecordingSheet(
    formattedRecordDuration: String,
    isRecording: Boolean,
    onDismiss: () -> Unit,
    onPauseClick: () -> Unit,
    onResumeClick: () -> Unit,
    onCompleteRecording: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.onSurface
    ) {
        SheetContent(
            formattedRecordDuration = formattedRecordDuration,
            isRecording = isRecording,
            onDismiss = onDismiss,
            onPauseClick = onPauseClick,
            onResumeClick = onResumeClick,
            onCompleteRecording = onCompleteRecording,
        )
    }
}

@Composable
fun SheetContent(
    formattedRecordDuration: String,
    isRecording: Boolean,
    onDismiss: () -> Unit,
    onPauseClick: () -> Unit,
    onResumeClick: () -> Unit,
    onCompleteRecording: () -> Unit,
    modifier: Modifier = Modifier
) {
    val primaryBubbleSize = PRIMARY_BUTTON_BUBBLE_SIZE_DP.dp
    val secondaryButtonSize = SECONDARY_BUTTON_SIZE_DP.dp

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (isRecording) {
                    stringResource(R.string.recording_your_memories)
                } else {
                    stringResource(R.string.recording_paused)
                },
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
            )
            Text(
                text = formattedRecordDuration,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFeatureSettings = "tnum",
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .defaultMinSize(minWidth = 100.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledIconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .size(secondaryButtonSize),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.cancel_recording)
                )
            }

            EchoBubbleFloatingActionButton(
                showBubble = isRecording,
                onClick = if (isRecording) {
                    onCompleteRecording
                } else onResumeClick,
                icon = {
                    Icon(
                        imageVector = if (isRecording) {
                            Icons.Default.Check
                        } else Icons.Filled.Microphone,
                        contentDescription = if (isRecording) {
                            stringResource(R.string.finish_recording)
                        } else {
                            stringResource(R.string.resume_recording)
                        },
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                primaryButtonSize = 72.dp,
            )

            FilledIconButton(
                onClick = if (isRecording) {
                    onPauseClick
                } else onCompleteRecording,
                modifier = Modifier
                    .size(secondaryButtonSize),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = if (isRecording) {
                        Icons.Filled.Pause
                    } else {
                        Icons.Default.Check
                    },
                    contentDescription = if (isRecording) {
                        stringResource(R.string.pause_recording)
                    } else {
                        stringResource(R.string.finish_recording)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SheetPreview() {
    EchoJournalTheme {
        SheetContent(
            formattedRecordDuration = "00:10:40",
            isRecording = true,
            onDismiss = {},
            onPauseClick = {},
            onResumeClick = {},
            onCompleteRecording = {}
        )
    }
}