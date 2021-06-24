package com.koti.testapp.di

import android.content.Context
import com.koti.testapp.db.DataCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author koti
 * Module to provide application level objects
 */
@Module
@InstallIn(SingletonComponent::class)
class AppLevelModule {

    @Provides
    @Singleton
    fun provideDataCache(@ApplicationContext context: Context) = DataCache(context)

}