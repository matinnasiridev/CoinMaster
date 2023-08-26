package ir.groid.coinmaster.di


import androidx.room.Room
import ir.groid.coinmaster.api.provideApiService
import ir.groid.coinmaster.database.AppDatabase
import ir.groid.coinmaster.repository.AppRepository
import ir.groid.coinmaster.util.Constans
import ir.groid.coinmaster.util.GlideImageLoader
import ir.groid.coinmaster.util.NetworkChecker
import ir.groid.coinmaster.viewModels.CoinDataVM
import ir.groid.coinmaster.viewModels.MarketVM
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single { provideApiService() }

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            Constans.DatabaseName
        ).allowMainThreadQueries()
            .build()
    }
    single { get<AppDatabase>().coinDao }
    single { get<AppDatabase>().newsDao }


    single { AppRepository(get(), get(), get()) }
    single { NetworkChecker(get()) }
    single<AppService.ImageLoader> { GlideImageLoader() }


    viewModel { MarketVM(get(), get()) }
    viewModel { CoinDataVM(get()) }
}