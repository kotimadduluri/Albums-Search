package com.koti.testapp.db.roomDB

import androidx.room.Embedded
import androidx.room.Relation
import com.koti.testapp.network.response.Contributor

data class RepoWithContributors(
    @Embedded
    var repoEntity: RepoEntity,
     @Relation(
         parentColumn = "_id",
         entityColumn = "parentRepoId"
     )
     var contributors: List<ContributorEntity>
)
