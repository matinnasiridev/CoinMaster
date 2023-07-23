package ir.groid.coinmaster.di

import ir.groid.coinmaster.viewModels.CoinDataVM
import ir.groid.coinmaster.viewModels.MarketVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelM {
    val ViewModelM = module {
        viewModel { MarketVM(get()) }
        viewModel { CoinDataVM(get()) }
    }
}