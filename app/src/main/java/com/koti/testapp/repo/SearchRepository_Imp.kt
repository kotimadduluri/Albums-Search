package com.koti.testapp.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.koti.testapp.db.DataCache
import com.koti.testapp.db.roomDB.RepoEntityDao
import com.koti.testapp.network.NetworkResponse
import com.koti.testapp.network.SearchApi
import com.koti.testapp.network.response.Contributor
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * @author koti
 * Repository implimentation
 */
class SearchRepository_Imp @Inject constructor(
    private val searchApi: SearchApi,
    private val dataCache: DataCache,
    private val repoEntityDao: RepoEntityDao
) :SearchRepository {

    private val networkResponse = MutableLiveData<NetworkResponse<String>>()

    override fun getDataCache()=repoEntityDao

    override fun getNetworkObserver()=networkResponse

    override suspend fun search(query: String, page: Int, pageSize: Int)=searchApi.search(query, page = page, perPage = pageSize)

    /**
     * methode to search based on user query
     * @param query user
     */
    override fun getContributors(name: String, login: String) = liveData(Dispatchers.IO) {
        try {
            println("contributors searching for ::: $name")
            emit(searchApi.getContributors(name, login))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(arrayListOf<Contributor>())
        }
    }
}