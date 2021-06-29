package com.koti.testapp.db.roomDB


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = RepoEntity::class,
    parentColumns = arrayOf("_id"),
    childColumns = arrayOf("parentRepoId"),
    onDelete = CASCADE)]
)
data class ContributorEntity(
    @PrimaryKey var _cid: Long,
    var parentRepoId: Int,
    var avatarUrl: String,
    var login: String
)