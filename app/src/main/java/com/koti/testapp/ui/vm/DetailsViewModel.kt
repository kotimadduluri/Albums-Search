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
class DetailsViewModel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel() {
    fun getContributors(name:String,login:String)=searchRepository.getContributors(name,login)
}