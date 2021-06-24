package com.koti.testapp.helper

import com.koti.testapp.db.roomDB.RepoEntity
import com.koti.testapp.network.response.Item

/**
 * @author koti
 * Class to convert raw data
 */
object DataConvertor {

    fun rawToRepoEntityList(data:List<Item>)=ArrayList<RepoEntity>().apply {
        for (item in data){
            add(rawToRepoEntity(item))
        }
    }

    private fun rawToRepoEntity(data:Item)=RepoEntity(
        _id = data.id,
        avatar = data.owner.avatarUrl,
        description = data.description,
        url = data.htmlUrl,
        name = data.name,
        fullName = data.fullName,
        loginName = data.owner.login,
        updatedTime = data.updatedAt
    )
}