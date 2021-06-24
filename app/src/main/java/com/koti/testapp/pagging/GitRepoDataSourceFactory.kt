package com.koti.testapp.pagging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.koti.testapp.db.roomDB.RepoEntity
import com.koti.testapp.network.NetworkResponse
import com.koti.testapp.network.response.Item
import com.koti.testapp.repo.SearchRepository

/**
 * @author koti
 * Factory class to provide pagination
 */
class GitRepoDataSourceFactory(
    private val searchRepository: SearchRepository,
    private val networkResponse: MutableLiveData<NetworkResponse<String>>
) : DataSource.Factory<Int, RepoEntity>() {

    private val newsDataSourceLiveData = MutableLiveData<GitRepoDataSource>()
    private var query = ""
    private var isFirstLoad = true

    override fun create(): DataSource<Int, RepoEntity> {
        val newsDataSource =
            GitRepoDataSource(searchRepository, networkResponse, query, isFirstLoad)
        newsDataSourceLiveData.postValue(newsDataSource)
        isFirstLoad = false
        return newsDataSource
    }

    /**
     * @param newQuery user given key to search
     */
    fun search(newQuery: String) {
        query = newQuery
        refresh()
    }

    //method to reset data
    private fun refresh() {
        newsDataSourceLiveData.value?.invalidate()
    }
}