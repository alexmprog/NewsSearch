package com.renovavision.newssearch.ui.article.list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.renovavision.newssearch.data.model.livedata.Resource
import com.renovavision.newssearch.data.model.local.Article
import com.renovavision.newssearch.data.repository.ArticlesRepository
import com.renovavision.newssearch.utils.Constants
import com.renovavision.newssearch.utils.scheduler.SchedulerProvider
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class ArticleListViewModel @Inject constructor(private val articlesRepository: ArticlesRepository,
                                               private val schedulerProvider: SchedulerProvider) : ViewModel() {

    val articlesData: MutableLiveData<Resource<List<Article>, String>> = MutableLiveData()

    var currentPage = 1

    private var disposable: Disposable? = null

    fun loadNextData() {
        loadArticles(currentPage, Constants.PAGE_SIZE)
    }

    private fun loadArticles(page: Int, pageSize: Int) {
        if (page == 1) {
            articlesData.postValue(Resource.loading())
        }
        articlesRepository.getArticles(page, pageSize)
                .compose(schedulerProvider.getSchedulersForObservable())
                .subscribe({
                    currentPage++
                    articlesData.postValue(Resource.success(it))
                }, {
                    Timber.e(ArticleListViewModel::class.java.simpleName, it.message)
                })
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}