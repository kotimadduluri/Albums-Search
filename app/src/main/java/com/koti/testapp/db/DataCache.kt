package com.koti.testapp.db

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koti.testapp.network.response.Item
import javax.inject.Inject


class DataCache @Inject constructor(val context: Context) {

    companion object{
        const val PREF_NAME="_rep_cache"
        const val KEY_DATA="_data"
    }

    private val preference= context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setData(data: List<Item>){
        try{
            val jsonString=Gson().toJson(data)
            preference.edit(commit = true){
                putString(KEY_DATA, jsonString)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun getData(): ArrayList<Item> {
        try{
            val jsonString=preference.getString(KEY_DATA, null)
            jsonString?.let {
                val listType = object : TypeToken<ArrayList<Item?>?>() {}.type
                listType?.let { type->
                    return Gson().fromJson(it, type)
                }
            }.apply {
                return arrayListOf<Item>()
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return arrayListOf<Item>()
    }

}