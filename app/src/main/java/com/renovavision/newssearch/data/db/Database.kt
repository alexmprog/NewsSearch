package com.renovavision.newssearch.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.renovavision.newssearch.data.model.local.Article

@Database(entities = [Article::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao
}