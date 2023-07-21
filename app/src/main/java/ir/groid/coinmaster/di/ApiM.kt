package ir.groid.coinmaster.di

import ir.groid.coinmaster.api.ApiService
import ir.groid.coinmaster.util.Constans
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiM {
    val ApiM = module {
        single { provideRetrofit() }
        single { provideApiService(get()) }
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
}