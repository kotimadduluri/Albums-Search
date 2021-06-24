package com.koti.testapp.di

import android.content.Context
import androidx.room.Room
import com.koti.testapp.db.DataCache
import com.koti.testapp.db.roomDB.DB_NAME
import com.koti.testapp.db.roomDB.RepoEntityDataBase
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

    @Provides
    @Singleton
    fun provideRepoEntityDataBase(@ApplicationContext context: Context):RepoEntityDataBase{
        return Room.databaseBuilder(
            context.applicationContext,
            RepoEntityDataBase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepoEntityDao(repoDataBase: RepoEntityDataBase)=repoDataBase.repoEntityDao()

}