package com.koti.testapp.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.koti.testapp.db.DataCache
import com.koti.testapp.db.roomDB.ContributorEntity
import com.koti.testapp.db.roomDB.ContributorEntityDao
import com.koti.testapp.db.roomDB.RepoEntityDao
import com.koti.testapp.helper.DataConvertor
import com.koti.testapp.helper.RepoUpdateReciver
import com.koti.testapp.network.NetworkResponse
import com.koti.testapp.network.SearchApi
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * @author koti
 * Repository implimentation
 */
class SearchRepository_Imp @Inject constructor(
    private val searchApi: SearchApi,
    private val dataCache: DataCache,
    private val repoEntityDao: RepoEntityDao,
    private val contributorEntityDao: ContributorEntityDao,
    private val repoUpdateReciver: RepoUpdateReciver
) :SearchRepository {

    private val networkResponse = MutableLiveData<NetworkResponse<String>>()

    override fun getRepoUpdateReciver()=repoUpdateReciver

    override fun getDataCache()=repoEntityDao

    override fun getContributorEntityDao()=contributorEntityDao

    override fun getNetworkObserver()=networkResponse

    override suspend fun search(query: String, page: Int, pageSize: Int)=searchApi.search(query, page = page, perPage = pageSize)

    /**
     * methode to search based on user query
     * @param query user
     */
    override fun getContributors(name: String, login: String, _id: Int): LiveData<List<ContributorEntity>> = liveData(Dispatchers.IO) {
        try {
            println("contributors searching for ::: $name")
            val result=searchApi.getContributors(name, login)
            if(!result.isNullOrEmpty()){
                val proccessedData=DataConvertor.rawToContributorList(result,_id)
                contributorEntityDao.insertAll(proccessedData)
                emit(proccessedData)
                getRepoUpdateReciver().setDataUpdate(_id)
            }else{
                emit(arrayListOf<ContributorEntity>())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(arrayListOf<ContributorEntity>())
        }
    }

    override fun getRepoById(_id: Int)=repoEntityDao.getRepoWithContributorsById(_id)

    override fun addNewPerson(name: String, repoId: Int) = liveData(Dispatchers.IO) {
        try {
            val user=ContributorEntity(
                _cid = System.currentTimeMillis(),
                login =name,
                avatarUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRnjj6PeSIOKEaRUwTfPz5JX9UwjhVbn5sE-Q&usqp=CAU",
                parentRepoId = repoId
            )
            println("adding contributor ::: $user")
            val insertId=contributorEntityDao.insert(user)
            if(insertId>0){
                getRepoUpdateReciver().setDataUpdate(repoId)
                emit(user)
            }else{
                emit(null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(null)
        }
    }
}