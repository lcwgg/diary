package com.example.fruitsdiary.dagger

import com.example.fruitsdiary.network.FruitsDiaryService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    private val SERVER_URL = "https://fruitdiary.test.themobilelife.com/api/"

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                    .build()

    @Provides
    fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    fun provideFruitsDiaryService(retrofit: Retrofit): FruitsDiaryService =
            retrofit.create(FruitsDiaryService::class.java)

}
