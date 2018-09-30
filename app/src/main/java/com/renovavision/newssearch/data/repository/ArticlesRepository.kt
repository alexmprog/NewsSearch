package com.renovavision.newssearch.data.repository

import com.renovavision.newssearch.data.api.ArticleService
import com.renovavision.newssearch.data.db.ArticlesDao
import com.renovavision.newssearch.data.model.local.Article
import com.renovavision.newssearch.utils.Utils
import io.reactivex.Observable
import javax.inject.Inject

class ArticlesRepository @Inject constructor(val articlesService: ArticleService,
                                             val articlesDao: ArticlesDao, val utils: Utils) {

    fun getArticles(page: Int, pageSize: Int): Observable<List<Article>> {
        val hasConnection = utils.isConnectedToInternet()
        var observableFromApi: Observable<List<Article>>? = null
        if (hasConnection) {
            observableFromApi = getArticlesFromApi(page, pageSize)
        }
        val observableFromDb = getArticlesFromDb(page, pageSize)
        return if (hasConnection) observableFromApi ?: observableFromDb else observableFromDb
    }

    private fun getArticlesFromApi(page: Int, pageSize: Int): Observable<List<Article>> {
        return articlesService.getArticles(page, pageSize).map {
            val data = it.articleList.data
            if (data.isNotEmpty()) {
                articlesDao.insertAllArticles(data)
            }
            data
        }
    }

    private fun getArticlesFromDb(page: Int, pageSize: Int): Observable<List<Article>> {
        return articlesDao.getArticles(pageSize, (page - 1) * pageSize)
                .toObservable()
    }
}