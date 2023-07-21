package ir.groid.coinmaster.di

import ir.groid.coinmaster.api.ApiManager
import ir.groid.coinmaster.repository.AppRepository
import org.koin.dsl.module

object ToolsM {

    val ToolsM = module {

        single { ApiManager(get()) }
        single { AppRepository(get(), get()) }
    }
}