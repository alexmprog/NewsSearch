package com.renovavision.newssearch.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.renovavision.newssearch.data.model.local.Article
import io.reactivex.Single

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM articles ORDER BY lastModified limit :limit offset :offset")
    fun getArticles(limit: Int, offset: Int): Single<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: Article)

    @Insert(
            onConflict = OnConflictStrategy.REPLACE
    )
    fun insertAllArticles(articles: List<Article>)
}