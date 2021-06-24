package com.koti.testapp.db.roomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepoEntityDao {

    @Query("SELECT * FROM RepoEntity")
    fun getAll(): List<RepoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<RepoEntity>)
}