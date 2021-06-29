package com.koti.testapp.db.roomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContributorEntityDao {

    @Query("SELECT * FROM contributorentity where parentRepoId=:repoId")
    fun getAll(repoId:Int): List<ContributorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<ContributorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: ContributorEntity):Long
}