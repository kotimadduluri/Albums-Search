package com.koti.testapp.db.roomDB

import androidx.room.*

@Dao
interface RepoEntityDao {

    @Query("SELECT * FROM RepoEntity")
    fun getAll(): List<RepoEntity>

    @Transaction
    @Query("SELECT * FROM RepoEntity")
    fun getAllWithContributors(): List<RepoWithContributors>

    @Transaction
    @Query("SELECT * FROM RepoEntity where _id=:repoId")
    fun getRepoWithContributorsById(repoId:Int): RepoWithContributors

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<RepoEntity>)

    @Query("Delete from RepoEntity")
    fun deleteAll()
}