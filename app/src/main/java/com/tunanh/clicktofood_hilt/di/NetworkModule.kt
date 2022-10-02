package com.tunanh.clicktofood_hilt.di

import com.tunanh.clicktofood_hilt.data.remote.service.Service
import com.tunanh.clicktofood_hilt.util.AppConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL_NEW = "https://www.themealdb.com/api/json/v1/1/"


    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_NEW)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): Service =
        retrofit.create(Service::class.java)


    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//            .addInterceptor(NetworkInterceptor())
            .connectTimeout(AppConfig.Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(AppConfig.Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
}