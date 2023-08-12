package ir.groid.coinmaster.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import ir.groid.coinmaster.api.ApiService
import ir.groid.coinmaster.database.AppDatabase
import ir.groid.coinmaster.database.CoinDao
import ir.groid.coinmaster.database.NewsDao
import ir.groid.coinmaster.model.RCoinData
import ir.groid.coinmaster.model.RNewsData
import ir.groid.coinmaster.repository.AppRepository
import ir.groid.coinmaster.util.Constans
import ir.groid.coinmaster.viewModels.CoinDataVM
import ir.groid.coinmaster.viewModels.MarketVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {
    val ApiM = module {
        single { provideRetrofit() }
        single { provideApiService(get()) }
    }

    val DatabaseM = module {

        single { provideDatabase(get()) }
        factory { provideCoinDao(get()) }
        factory { provideNewsDao(get()) }
        factory { provideRCoinData() }
        factory { provideRNewsData() }
    }

    val ToolsM = module {
        single { AppRepository(get(), get(), get()) }
        // single { (c: Context) -> Glide.with(c) }
    }

    val ViewModelM = module {
        viewModel { MarketVM(get()) }
        viewModel { CoinDataVM(get()) }
    }

    private fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(Constans.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun provideApiService(r: Retrofit): ApiService = r.create(ApiService::class.java)


    private fun provideDatabase(c: Context): AppDatabase {
        return Room.databaseBuilder(
            c,
            AppDatabase::class.java,
            Constans.DatabaseName
        ).allowMainThreadQueries()
            .build()
    }

    private fun provideCoinDao(db: AppDatabase): CoinDao = db.coinDao


    private fun provideNewsDao(db: AppDatabase): NewsDao = db.newsDao


    private fun provideRCoinData(): RCoinData = RCoinData()

    private fun provideRNewsData(): RNewsData = RNewsData()


}