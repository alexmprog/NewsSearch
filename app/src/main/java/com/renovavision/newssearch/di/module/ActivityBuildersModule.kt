package com.renovavision.newssearch.di.module

import com.renovavision.newssearch.di.scope.ActivityScope
import com.renovavision.newssearch.ui.article.details.ArticleDetailsActivity
import com.renovavision.newssearch.ui.article.list.ArticleListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindArticleListActivity(): ArticleListActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindArticleDetailsActivity(): ArticleDetailsActivity

}