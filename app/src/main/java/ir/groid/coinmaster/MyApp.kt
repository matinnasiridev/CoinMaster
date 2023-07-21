package ir.groid.coinmaster

import android.app.Application
import ir.groid.coinmaster.di.ApiM
import ir.groid.coinmaster.di.DatabaseM
import ir.groid.coinmaster.di.ToolsM
import ir.groid.coinmaster.di.ViewModelM
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()


        // Di Use Koin v3.4.2
        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    // Module Repository
                    DatabaseM.DatabaseM,
                    ApiM.ApiM,

                    // Module Tools
                    ToolsM.ToolsM,

                    // ViewModel
                    ViewModelM.ViewModelM

                )
            )
        }

    }
}