package com.koti.testapp.ui.vm

import androidx.lifecycle.ViewModel
import com.koti.testapp.repo.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author koti
 * @param searchRepository [SearchRepository] repository
 *
 * //add new user
 * and capture to db
 */
@HiltViewModel
class DetailsViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {
    fun getContributors(name: String, login: String, _id: Int) = searchRepository.getContributors(name, login,_id)

    fun getRepoById(repoId: Int)=searchRepository.getRepoById(repoId)

    fun addNewPerson(name: String, repoId: Int)=searchRepository.addNewPerson(name,repoId)

    fun checkDb(){
        val data=searchRepository.getDataCache().getAllWithContributors()
        println("cached=====>$data")
    }
}