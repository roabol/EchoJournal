package br.com.sigmaonline.echojournal.app.widget

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Column
import br.com.sigmaonline.echojournal.R
import br.com.sigmaonline.echojournal.app.MainActivity
import br.com.sigmaonline.echojournal.app.navigation.ACTION_CREATE_ECHO

class RecordEchoWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = RecordEchoWidget()
}

class RecordEchoWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val recordNewEcho = context.getString(R.string.record_new_echo)
        provideContent {
            GlanceTheme {
                Column(
                    modifier = GlanceModifier
                        .clickable(
                            onClick = actionStartActivity(
                                Intent(context, MainActivity::class.java).apply {
                                    data = "https://echojournal.com/echos/true".toUri()
                                    action = ACTION_CREATE_ECHO
                                }
                            )
                        )

//                        A versão abaixo foi substituida pela de cima, pois estava gerando um
//                        erro está relacionado ao BAL (Background Activity Launch) no Android,
//                        uma política de segurança que impede que apps iniciem atividades
//                        diretamente a partir de segundo plano, especialmente quando não há
//                        interação visível do usuário. A partir do Android 10 e mais
//                        estritamente no Android 12+, essas restrições se tornaram mais severas.
//
//                        .clickable {
//
//                            val intent = Intent(context, MainActivity::class.java).also {
//                                it.data = "https://echojournal.com/echos/true".toUri()
//                                it.action = ACTION_CREATE_ECHO
//                            }
//                            val pendingIntent = TaskStackBuilder
//                                .create(context)
//                                .addNextIntentWithParentStack(intent)
//                                .getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
//
//                            pendingIntent?.send()
//                        }
                ) {
                    Image(
                        provider = ImageProvider(R.drawable.widget),
                        contentDescription = recordNewEcho
                    )
                }
            }
        }
    }

}