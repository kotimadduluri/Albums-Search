package com.koti.testapp.ui.view

import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.koti.testapp.R
import com.koti.testapp.extentions.textChanges
import com.koti.testapp.network.NetworkResponse
import com.koti.testapp.ui.adapter.RepositoriesAdapter
import com.koti.testapp.ui.adapter.RepositoriesClickListener
import com.koti.testapp.ui.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @author koti
 * activity to shoe repositoryListD
 */
@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main), RepositoriesClickListener {


    private val repositoriesAdapter: RepositoriesAdapter by lazy {
        RepositoriesAdapter(this)
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var searchPlaceHolder: TextView
    private lateinit var searchInput: EditText
    private lateinit var noDataHolder: LinearLayout

    override fun initViewmodel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun handleIntent() {
        //u can handle required action
    }

    override fun initView() {
        setupList()
        noDataHolder = findViewById(R.id.noDataHolder)
        searchPlaceHolder = findViewById(R.id.searchPlaceHolder)
        searchInput = findViewById<EditText>(R.id.searchInput).apply {
            textChanges()
                .filterNot { it.isNullOrBlank() }
                .debounce(1000)
                .onEach {
                    viewModel.search(it.toString())
                }
                .launchIn(CoroutineScope(Dispatchers.IO))
        }
    }

    private fun setupList() {
        findViewById<RecyclerView>(R.id._rvList).apply {
            adapter = repositoriesAdapter
        }
    }

    //to start required observers
    override fun startObservers() {
        observerRepositoryDataChanges()
        observeNetwork()
        observerRepoDataChnages()
    }

    private fun observerRepoDataChnages() {
        viewModel.repoUpdateReciver.observe(this,{
            println("data update ====>$it")
            if(it.first){
                viewModel.resetReceiver()
                if(it.second>0){
                    val newRepoData=viewModel.getRepoById(it.second)
                    repositoriesAdapter.updateData(newRepoData)
                }
            }
        })
    }

    private fun observerRepositoryDataChanges() {
        viewModel.repositorySearchResult.observe(this, {
            repositoriesAdapter.submitList(it)
            handleNoData()
        })
    }

    //to observe network call status
    private fun observeNetwork() {
        viewModel.networkResponse.observe(this, { networkResponse ->
            when (networkResponse.status) {
                NetworkResponse.STATUS.INPROGRESS -> {
                    if (this::searchInput.isInitialized) {
                        enableSearchPlaceHolder(searchInput.text.toString())
                    }
                }
                NetworkResponse.STATUS.ERROR,
                NetworkResponse.STATUS.SUCCESS -> {
                    enableSearchPlaceHolder("")
                    handleNoData()
                }
                else -> {
                    enableSearchPlaceHolder("")
                }
            }
        })
    }

    private fun enableSearchPlaceHolder(text: String) {
        try {
            if (this::searchPlaceHolder.isInitialized) {
                if (text.trim().isNotEmpty()) {
                    searchPlaceHolder.text = "Search for '$text'"
                    searchPlaceHolder.visibility = View.VISIBLE
                    noDataHolder.visibility = View.GONE
                } else {
                    searchPlaceHolder.text = ""
                    searchPlaceHolder.visibility = View.GONE
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun handleNoData() {
        try {
            if (repositoriesAdapter.itemCount > 0) {
                noDataHolder.visibility = View.GONE
            } else {
                noDataHolder.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRepositoriesClick(reposId: Int) {
        DetailsActivity.showDetails(this, reposId)
    }
}