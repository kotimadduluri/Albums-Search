package com.koti.testapp.pagging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
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
) : PageKeyedDataSource<Int, Item>() {
    private val PAGE = 1

    //initial page loading
    override fun loadInitial(
        params: PageKeyedDataSource.LoadInitialParams<Int>,
        callback: PageKeyedDataSource.LoadInitialCallback<Int, Item>
    ) {
        if (searchKey.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    networkResponse.postValue(NetworkResponse.Loading())
                    val response =
                        searchRepository.search(searchKey, PAGE, params.requestedLoadSize)
                    callback.onResult(
                        response.items, null,
                        if (response.items.isNotEmpty()) PAGE + 1 else -1
                    )
                    searchRepository.getDataCache()
                        .setData(response.items) //caching data into preference
                    networkResponse.postValue(NetworkResponse.Success("Loaded"))
                } catch (e: Exception) {
                    networkResponse.postValue(NetworkResponse.Error())
                    e.printStackTrace()
                }
            }
        } else if (isFirstLoad) {
            val cache = searchRepository.getDataCache().getData()
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
        callback: PageKeyedDataSource.LoadCallback<Int, Item>
    ) {
        //we are loading data in one direction so not required
    }

    //to load next pages
    override fun loadAfter(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, Item>
    ) {
        if (searchKey.isNotEmpty() && params.key > 0) {
            val page = params.key
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = searchRepository.search(searchKey, page)
                    callback.onResult(
                        response.items,
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