package ir.groid.coinmaster

import android.app.Application
import ir.groid.coinmaster.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Koin v3.4.2
        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    appModule
                )
            )
        }
    }
}