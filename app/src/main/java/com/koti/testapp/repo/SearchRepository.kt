package com.koti.testapp.repo

import androidx.lifecycle.liveData
import com.koti.testapp.network.SearchApi
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * @author koti
 * Repository to search
 */
class SearchRepository @Inject constructor(private  val searchApi: SearchApi) {
    private val searchResult=null

    /**
     * methode to search based on user query
     * @param query user
     */
    fun search(query:String)= liveData(Dispatchers.IO) {
      //  emit(query)
        println("repo query ::: $query")
        emit(searchApi.search("GitHub"))
    }
}