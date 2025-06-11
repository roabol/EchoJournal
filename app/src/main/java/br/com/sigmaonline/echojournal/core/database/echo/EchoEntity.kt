package br.com.sigmaonline.echojournal.core.database.echo

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.sigmaonline.echojournal.echos.domain.echo.Mood

@Entity
data class EchoEntity(
    @PrimaryKey(autoGenerate = true)
    val echoId: Int = 0,
    val title: String,
    val mood: Mood,
    val recordedAt: Long,
    val note: String?,
    val audioFilePath: String,
    val audioPlaybackLength: Long,
    val audioAmplitudes: List<Float>,
)
