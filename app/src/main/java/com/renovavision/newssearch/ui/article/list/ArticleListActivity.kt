package com.renovavision.newssearch.ui.article.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import com.renovavision.newssearch.R
import com.renovavision.newssearch.data.model.livedata.ResourceState
import com.renovavision.newssearch.data.model.local.Article
import com.renovavision.newssearch.ui.article.details.ArticleDetailsActivity
import com.renovavision.newssearch.ui.common.base.BaseActivity
import com.renovavision.newssearch.ui.common.widget.recycler.DividerItemDecoration
import com.renovavision.newssearch.ui.common.widget.recycler.OnLoadMoreListener
import kotlinx.android.synthetic.main.activity_article_list.*
import javax.inject.Inject

class ArticleListActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: ArticleListViewModel

    private var articlesAdapter: ArticlesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_list)

        initializeRecycler()

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ArticleListViewModel::class.java]
        viewModel.loadNextData()
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.articlesData.observe(this, Observer { it ->
            when (it?.status) {
                ResourceState.LOADING -> {
                    recycler_view?.showProgressView()
                }
                ResourceState.ERROR -> {
                    recycler_view?.hideProgressView()
                    articlesAdapter?.setLoadedError()
                    it.error?.let {
                        Snackbar.make(root_container, it, Snackbar.LENGTH_LONG).show()
                    }
                }
                ResourceState.SUCCESS -> {
                    recycler_view?.hideProgressView()
                    it.data?.let {
                        articlesAdapter?.setItems(it)
                        articlesAdapter?.setLoaded(it.isNotEmpty())
                    }
                }
            }
        })
    }

    private fun initializeRecycler() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        articlesAdapter = ArticlesAdapter()
        articlesAdapter?.loadMoreListener = object : OnLoadMoreListener {
            override fun onLoadMore() {
                viewModel.loadNextData()
            }
        }
        articlesAdapter?.listener = object : ArticlesAdapter.Listener {
            override fun onArticleClicked(article: Article, imageView: ImageView) {
                openArticleDetails(article, imageView)
            }
        }
        recycler_view?.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = articlesAdapter
            addItemDecoration(DividerItemDecoration(context, R.drawable.bg_list_divider))
            setEmptyView(empty_text_view, false)
            setProgressView(progress_view)
        }
    }

    private fun openArticleDetails(article: Article, imageView: ImageView) {
        val newIntent = ArticleDetailsActivity.newIntent(this, article)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.transitionName = article.id
        }
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView,
                ViewCompat.getTransitionName(imageView))
        startActivity(newIntent, options.toBundle())
    }
}