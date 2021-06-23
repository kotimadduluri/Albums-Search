package com.koti.testapp.ui.vm

import androidx.lifecycle.ViewModel
import com.koti.testapp.repo.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author koti
 * @param searchRepository respostory
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel() {
    fun search(query:String)=searchRepository.search(query)
}