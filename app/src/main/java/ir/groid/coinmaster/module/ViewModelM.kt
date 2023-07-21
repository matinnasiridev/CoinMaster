package ir.groid.coinmaster.module

import ir.groid.coinmaster.viewModels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelM {
    val ViewModelM = module {
        viewModel { MainViewModel(get()) }
    }
}