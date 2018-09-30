package com.renovavision.newssearch.data.api

import com.renovavision.newssearch.data.model.remote.ArticleListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Alexandr Golovach on 13.09.2018.
 */

interface ArticleService {

    @GET("search?show-fields=thumbnail,lastModified")
    fun getArticles(@Query("page") page: Int?, @Query("pageSize") pageSize: Int?): Observable<ArticleListResponse>
}