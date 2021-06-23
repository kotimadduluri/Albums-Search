package com.koti.testapp.ui.view

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author koti
 * base activity to provide common methods
 */
@AndroidEntryPoint
abstract class BaseActivity(layout: Int) :AppCompatActivity(layout) {

    override fun onStart() {
        super.onStart()
        initViewmodel()
        initView()
        startObservers()
    }

    //to get view latout
    abstract fun getLayout():Int

    //to init viewmodels
    abstract fun initViewmodel()

    //to init individual views
    abstract fun initView()

    //listen to data changes
    abstract fun startObservers()

    //common methode to show messages
    fun showMessage(message: String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}