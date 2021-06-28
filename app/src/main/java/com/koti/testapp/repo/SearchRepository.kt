package com.koti.testapp.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.koti.testapp.db.DataCache
import com.koti.testapp.db.roomDB.RepoEntityDao
import com.koti.testapp.network.NetworkResponse
import com.koti.testapp.network.SearchApi
import com.koti.testapp.network.response.Contributor
import com.koti.testapp.network.response.GitRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * @author koti
 * Repository structure to search and get details of repository
 */
const val PAGE_DEFULT_SIZE = 10
const val PAGE_INITIAL_SIZE = 15

interface SearchRepository {

    fun getDataCache(): RepoEntityDao
    fun getNetworkObserver(): MutableLiveData<NetworkResponse<String>>

    //to search given query
    suspend fun search(query: String, page: Int, pageSize: Int = PAGE_DEFULT_SIZE): GitRepository

    /**
     * methode to search based on user query
     * @param query user
     */
    fun getContributors(name: String, login: String): LiveData<List<Contributor>>
}