package com.renovavision.newssearch.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.renovavision.newssearch.BuildConfig
import com.renovavision.newssearch.data.api.ArticleService
import com.renovavision.newssearch.data.api.AuthInterceptor
import com.renovavision.newssearch.data.api.adapter.ArticleAdapter
import com.renovavision.newssearch.data.model.local.Article
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    companion object {
        private const val HTTP_TIMEOUT: Long = 30
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(AuthInterceptor(BuildConfig.API_KEY))
        builder.readTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(HTTP_TIMEOUT * 3, TimeUnit.SECONDS)
        builder.connectTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
        return builder.build()
    }

    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().serializeNulls()
                .enableComplexMapKeySerialization()
                .registerTypeAdapter(Article::class.java, ArticleAdapter())
                .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .validateEagerly(true)
                .build()
    }

    @Singleton
    @Provides
    fun providesArticleService(retrofit: Retrofit): ArticleService {
        return retrofit.create(ArticleService::class.java)
    }

}
