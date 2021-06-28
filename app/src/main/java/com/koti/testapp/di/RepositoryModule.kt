package com.koti.testapp.di

import com.koti.testapp.db.DataCache
import com.koti.testapp.db.roomDB.RepoEntityDao
import com.koti.testapp.network.SearchApi
import com.koti.testapp.repo.SearchRepository
import com.koti.testapp.repo.SearchRepository_Imp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * @author koti
 * Module to supply different repertories
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideSearchRepo(searchApi: SearchApi, dataCache: DataCache,repoEntityDao: RepoEntityDao):SearchRepository =
        SearchRepository_Imp(
            searchApi,
            dataCache,
        repoEntityDao)

}