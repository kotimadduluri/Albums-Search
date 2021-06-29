package com.koti.testapp.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.koti.testapp.db.roomDB.ContributorEntity
import com.koti.testapp.db.roomDB.ContributorEntityDao
import com.koti.testapp.db.roomDB.RepoEntityDao
import com.koti.testapp.db.roomDB.RepoWithContributors
import com.koti.testapp.helper.RepoUpdateReciver
import com.koti.testapp.network.NetworkResponse
import com.koti.testapp.network.response.Contributor
import com.koti.testapp.network.response.GitRepository
import java.util.*

/**
 * @author koti
 * Repository structure to search and get details of repository
 */
const val PAGE_DEFULT_SIZE = 10
const val PAGE_INITIAL_SIZE = 15

interface SearchRepository {

    fun getRepoUpdateReciver(): RepoUpdateReciver

    fun getDataCache(): RepoEntityDao

    fun getContributorEntityDao(): ContributorEntityDao

    fun getNetworkObserver(): MutableLiveData<NetworkResponse<String>>

    //to search given query
    suspend fun search(query: String, page: Int, pageSize: Int = PAGE_DEFULT_SIZE): GitRepository

    /**
     * methode to search based on user query
     * @param query user
     */
    fun getContributors(name: String, login: String, _id: Int): LiveData<List<ContributorEntity>>

    fun getRepoById(_id: Int): RepoWithContributors

    fun addNewPerson(name:String, repoId: Int) : LiveData<ContributorEntity?>  //for dummy return
}