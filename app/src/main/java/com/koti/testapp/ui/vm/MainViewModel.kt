package com.koti.testapp.ui.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.koti.testapp.db.roomDB.RepoEntity
import com.koti.testapp.db.roomDB.RepoWithContributors
import com.koti.testapp.network.response.Item
import com.koti.testapp.pagging.GitRepoDataSourceFactory
import com.koti.testapp.repo.PAGE_DEFULT_SIZE
import com.koti.testapp.repo.PAGE_INITIAL_SIZE
import com.koti.testapp.repo.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author koti
 * @param searchRepository [SearchRepository] repository
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {
    private val TAG = "MainViewModel"
    var repositorySearchResult: LiveData<PagedList<RepoWithContributors>>
    val networkResponse = searchRepository.getNetworkObserver()
    val repoUpdateReciver = searchRepository.getRepoUpdateReciver().getReceiver()

    private val gitRepoDataSourceFactory: GitRepoDataSourceFactory by lazy {
        GitRepoDataSourceFactory(searchRepository, networkResponse)
    }

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_DEFULT_SIZE)
            .setInitialLoadSizeHint(PAGE_INITIAL_SIZE)
            .setEnablePlaceholders(false)
            .build()
        repositorySearchResult =
            LivePagedListBuilder<Int, RepoWithContributors>(gitRepoDataSourceFactory, config).build()
    }

    fun search(query: String) {
        Log.i(TAG, "searching :: $query")
        gitRepoDataSourceFactory.search(query)
    }

    fun resetReceiver() {
        searchRepository.getRepoUpdateReciver().resetDataUpdate()
    }

    fun getRepoById(repoId: Int)=searchRepository.getRepoById(repoId)
}