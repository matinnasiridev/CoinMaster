package ir.groid.coinmaster.di


import androidx.room.Room
import ir.groid.coinmaster.api.provideApiService
import ir.groid.coinmaster.database.AppDatabase
import ir.groid.coinmaster.repository.AppRepository
import ir.groid.coinmaster.util.AdsImpl
import ir.groid.coinmaster.util.Constans
import ir.groid.coinmaster.util.GlideImageLoader
import ir.groid.coinmaster.util.NetworkChecker
import ir.groid.coinmaster.viewModels.CoinDataVM
import ir.groid.coinmaster.viewModels.MarketVM
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            Constans.DatabaseName
        ).allowMainThreadQueries()
            .build()
    }

    single {
        AppRepository(
            provideApiService(),
            get<AppDatabase>().coinDao,
            get<AppDatabase>().newsDao
        )
    }

    single { NetworkChecker(get()) }
    single<AppService.AdsSystem> { AdsImpl() }
    single<AppService.ImageLoader> { GlideImageLoader() }


    viewModel { MarketVM(get(), get()) }
    viewModel { CoinDataVM(get()) }
}