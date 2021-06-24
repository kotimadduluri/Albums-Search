package com.koti.testapp.db.roomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * @author koti
 */
@Entity
data class RepoEntity(
    @PrimaryKey val _id: Int,
    @ColumnInfo(name = "avatar") val avatar: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "fullName") val fullName: String?,
    @ColumnInfo(name = "loginName") val loginName: String?,
    @ColumnInfo(name = "updatedTime") val updatedTime: String?
):Serializable