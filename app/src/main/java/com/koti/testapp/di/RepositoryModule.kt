package com.koti.testapp.di

import com.koti.testapp.network.SearchApi
import com.koti.testapp.repo.SearchRepository
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
    fun provideSearchRepo(searchApi: SearchApi)=SearchRepository(searchApi)

}