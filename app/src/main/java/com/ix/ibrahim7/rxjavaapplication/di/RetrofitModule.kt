package com.ix.ibrahim7.rxjavaapplication.di

import com.ix.ibrahim7.rxjavaapplication.network.MovieApi
import com.ix.ibrahim7.rxjavaapplication.other.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun instanceRetrofit(baseUrl: String) =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .apply {
                val builder = OkHttpClient.Builder()
                builder.connectTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                this.client(builder.build())
            }
            .build()


    @Provides
    @Singleton
    fun movieInterface() =
        instanceRetrofit(BASE_URL).create(MovieApi::class.java)


}