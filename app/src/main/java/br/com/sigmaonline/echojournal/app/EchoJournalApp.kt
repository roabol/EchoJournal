package br.com.sigmaonline.echojournal.app

import android.app.Application
import br.com.sigmaonline.echojournal.BuildConfig
import br.com.sigmaonline.echojournal.app.di.appModule
import br.com.sigmaonline.echojournal.core.database.di.databaseModule
import br.com.sigmaonline.echojournal.echos.di.echoModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class EchoJournalApp : Application() {

    val applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@EchoJournalApp)
            modules(
                appModule,
                echoModule,
                databaseModule
            )
        }
    }
}