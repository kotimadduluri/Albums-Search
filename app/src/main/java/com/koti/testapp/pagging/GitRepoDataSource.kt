package com.koti.testapp.pagging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.koti.testapp.db.roomDB.RepoEntity
import com.koti.testapp.helper.DataConvertor
import com.koti.testapp.network.NetworkResponse
import com.koti.testapp.network.response.Item
import com.koti.testapp.repo.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author koti
 * Data source to get data from api
 */
class GitRepoDataSource(
    private val searchRepository: SearchRepository,
    private val networkResponse: MutableLiveData<NetworkResponse<String>>,
    private val searchKey: String,
    private val isFirstLoad: Boolean
) : PageKeyedDataSource<Int, RepoEntity>() {
    private val PAGE = 1

    //initial page loading
    override fun loadInitial(
        params: PageKeyedDataSource.LoadInitialParams<Int>,
        callback: PageKeyedDataSource.LoadInitialCallback<Int, RepoEntity>
    ) {
        if (searchKey.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    networkResponse.postValue(NetworkResponse.Loading())
                    val response =
                        searchRepository.search(searchKey, PAGE, params.requestedLoadSize)
                    val processedData=DataConvertor.rawToRepoEntityList(response.items)
                    callback.onResult(
                        processedData, null,
                        if (response.items.isNotEmpty()) PAGE + 1 else -1
                    )
                    searchRepository.getDataCache().insertAll(processedData) //caching data into preference
                    networkResponse.postValue(NetworkResponse.Success("Loaded"))
                } catch (e: Exception) {
                    networkResponse.postValue(NetworkResponse.Error())
                    e.printStackTrace()
                }
            }
        } else if (isFirstLoad) {
            val cache = searchRepository.getDataCache().getAll()
            if (!cache.isNullOrEmpty()) {
                callback.onResult(
                    cache, null,
                    if (cache.isNotEmpty()) PAGE + 1 else -1
                )
            }
        }

    }

    //before pages. Not in use
    override fun loadBefore(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, RepoEntity>
    ) {
        //we are loading data in one direction so not required
    }

    //to load next pages
    override fun loadAfter(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, RepoEntity>
    ) {
        if (searchKey.isNotEmpty() && params.key > 0) {
            val page = params.key
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = searchRepository.search(searchKey, page)
                    val processedData=DataConvertor.rawToRepoEntityList(response.items)
                    callback.onResult(
                        processedData,
                        if (response.items.isNotEmpty()) PAGE + 1 else -1
                    )
                    networkResponse.postValue(NetworkResponse.Success("Loaded $page"))
                } catch (e: Exception) {
                    networkResponse.postValue(NetworkResponse.Error())
                    e.printStackTrace()
                }
            }
        }
    }
}