package com.renovavision.newssearch.ui.article.details

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.renovavision.newssearch.R
import com.renovavision.newssearch.data.model.local.Article
import com.renovavision.newssearch.ui.common.base.BaseActivity
import com.renovavision.newssearch.utils.Constants
import com.renovavision.newssearch.utils.DateTimeUtils
import com.renovavision.newssearch.utils.Utils
import com.renovavision.newssearch.utils.imageprocessing.WidthTransformation
import kotlinx.android.synthetic.main.activity_article_details.*
import javax.inject.Inject

class ArticleDetailsActivity : BaseActivity() {

    @Inject
    lateinit var utils: Utils

    private var article: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportPostponeEnterTransition()

        setContentView(R.layout.activity_article_details)

        article = intent.getSerializableExtra(Constants.KEY_ARTICLE_EXTRA) as Article
        initViews()
    }

    private fun initViews() {
        title_view?.text = article?.title
        section_view?.text = article?.section
        time_view?.text = DateTimeUtils.getYearTime(DateTimeUtils.getLocalDateTime(article?.lastModified))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            image_view?.transitionName = article?.id
        }

        val windowSize = utils.getWindowSize()
        Glide.with(image_view)
                .load(article?.thumbnail)
                .apply(RequestOptions()
                        .placeholder(R.drawable.bg_image_placeholder)
                        .transform(WidthTransformation(windowSize.x)))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        image_view?.layoutParams?.height = (windowSize.y * 0.4).toInt()
                        image_view?.setBackgroundColor(resources.getColor(R.color.colorAccentLight))
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }
                })
                .into(image_view)
    }

    companion object {

        fun newIntent(context: Context, article: Article): Intent {
            val intent = Intent(context, ArticleDetailsActivity::class.java)
            intent.putExtra(Constants.KEY_ARTICLE_EXTRA, article)
            return intent
        }
    }
}