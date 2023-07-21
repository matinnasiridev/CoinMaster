package ir.groid.coinmaster.di

import ir.groid.coinmaster.repository.AppRepository
import org.koin.dsl.module

object ToolsM {

    val ToolsM = module {

        single { AppRepository(get(),get(),get()) }
    }
}