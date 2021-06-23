package com.koti.testapp.ui.view

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.koti.testapp.R
import com.koti.testapp.ui.adapter.RepositoriesAdapter
import com.koti.testapp.ui.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main) {

    val adapter:RepositoriesAdapter by lazy {
        RepositoriesAdapter()
    }

    private lateinit var viewModel:MainViewModel

    override fun getLayout()=R.layout.activity_main

    override fun initViewmodel() {
        viewModel= ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun initView() {
        findViewById<RecyclerView>(R.id._rvList).adapter=adapter
    }

    override fun startObservers() {
        listenToSearchResult()
    }

    private fun listenToSearchResult() {
        viewModel.search("searching from base").observe(this,{
            it?.let { response->
                println("result ==> ${response.items.size}")
                if(response.items.isNotEmpty()){
                    adapter.submitData(response.items)
                }
            }
        })
    }
}