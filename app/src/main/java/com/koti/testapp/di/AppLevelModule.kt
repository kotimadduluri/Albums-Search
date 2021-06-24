package com.koti.testapp.di

import android.content.Context
import com.koti.testapp.BuildConfig
import com.koti.testapp.db.DataCache
import com.koti.testapp.network.SearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppLevelModule {

    @Provides
    @Singleton
    fun provideDataCache(@ApplicationContext context: Context) = DataCache(context)

}