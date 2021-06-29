package com.koti.testapp.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class RepoUpdateReciver @Inject constructor(){
    private val receiver=MutableLiveData<Pair<Boolean,Int>>(Pair(false,0))

    fun getReceiver():LiveData<Pair<Boolean,Int>> = receiver

    fun setDataUpdate(repoId:Int){
        receiver.postValue(Pair(true,repoId))
    }

    fun resetDataUpdate(){
        receiver.postValue(Pair(false,0))
    }
}