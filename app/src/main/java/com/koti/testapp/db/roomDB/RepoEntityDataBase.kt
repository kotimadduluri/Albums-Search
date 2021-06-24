package com.koti.testapp.db.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author koti
 * database to capture repos
 */

const val DB_NAME="_cache"
const val DB_VERSION=1
@Database(entities = [RepoEntity::class],version = DB_VERSION,exportSchema = false)
abstract class RepoEntityDataBase :  RoomDatabase(){
    abstract fun repoEntityDao():RepoEntityDao
}