package br.com.sigmaonline.echojournal.app.di

import br.com.sigmaonline.echojournal.app.EchoJournalApp
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as EchoJournalApp).applicationScope
    }
}