package ir.groid.coinmaster

import android.app.Application
import ir.groid.coinmaster.module.DatabaseM
import ir.groid.coinmaster.module.ToolsM
import ir.groid.coinmaster.module.ViewModelM
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()


        // Di Use Koin V3.4.2
        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    DatabaseM.DatabaseM,
                    ToolsM.ToolsM,
                    ViewModelM.ViewModelM
                )
            )
        }

    }
}