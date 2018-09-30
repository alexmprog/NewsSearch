package com.renovavision.newssearch.data.model.remote

import com.google.gson.annotations.SerializedName
import com.renovavision.newssearch.data.model.local.ArticleList

data class ArticleListResponse(@SerializedName("response") val articleList: ArticleList)