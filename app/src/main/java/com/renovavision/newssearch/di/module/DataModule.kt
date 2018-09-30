package com.renovavision.newssearch.di.module

import android.app.Application
import android.arch.persistence.room.Room
import com.renovavision.newssearch.data.api.ArticleService
import com.renovavision.newssearch.data.db.ArticlesDao
import com.renovavision.newssearch.data.db.Database
import com.renovavision.newssearch.data.repository.ArticlesRepository
import com.renovavision.newssearch.utils.Constants
import com.renovavision.newssearch.utils.Utils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): Database = Room.databaseBuilder(application,
            Database::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideArticlesDao(database: Database): ArticlesDao = database.articlesDao()

    @Singleton
    @Provides
    fun provideArticlesRepository(articleService: ArticleService, articlesDao: ArticlesDao, utils: Utils):
            ArticlesRepository = ArticlesRepository(articleService, articlesDao, utils)
}