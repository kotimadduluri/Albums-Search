package com.koti.testapp.repo

import androidx.lifecycle.liveData
import com.koti.testapp.network.SearchApi
import com.koti.testapp.network.response.Contributers
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * @author koti
 * Repository to search
 */
class SearchRepository @Inject constructor(private  val searchApi: SearchApi) {
    /**
     * methode to search based on user query
     * @param query user
     */
    fun search(query:String)= liveData(Dispatchers.IO) {
      //  emit(query)
        println("repo query ::: $query")
        emit(searchApi.search("GitHub"))
    }

    /**
     * methode to search based on user query
     * @param query user
     */
    fun getContributors(name:String,login:String)= liveData(Dispatchers.IO) {
        try {
            println("contributors searching for ::: $name")
            emit(searchApi.getContributors(name,login))
        }catch (e:Exception){
            e.printStackTrace()
            emit(arrayListOf<Contributers>())
        }
    }
}