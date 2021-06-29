package com.koti.testapp.helper

import com.koti.testapp.db.roomDB.ContributorEntity
import com.koti.testapp.db.roomDB.RepoEntity
import com.koti.testapp.db.roomDB.RepoWithContributors
import com.koti.testapp.network.response.Contributor
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

    fun rawToRepoEntityWithEmptyContributorsList(data:List<Item>)=ArrayList<RepoWithContributors>().apply {
        for (item in data){
            add(RepoWithContributors(rawToRepoEntity(item), arrayListOf()))
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

    fun rawToContributorList(data:List<Contributor>,repoId:Int)=ArrayList<ContributorEntity>().apply {
        for (item in data){
            add(rawToRepoEntity(item,repoId))
        }
    }

    private fun rawToRepoEntity(data:Contributor,repoId:Int)=ContributorEntity(
        _cid = data.id.toLong(),
        avatarUrl = data.avatarUrl,
        login = data.login,
        parentRepoId = repoId,
    )
}