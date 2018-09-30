package com.renovavision.newssearch.ui.article.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.renovavision.newssearch.App
import com.renovavision.newssearch.R
import com.renovavision.newssearch.data.model.local.Article
import com.renovavision.newssearch.ui.common.widget.recycler.EndlessRecyclerAdapter
import com.renovavision.newssearch.utils.DateTimeUtils
import com.renovavision.newssearch.utils.imageprocessing.RectangleTransformation
import kotlinx.android.synthetic.main.view_item_article.view.*

class ArticlesAdapter : EndlessRecyclerAdapter<Article>() {

    var listener: Listener? = null

    override fun createModelViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_item_article, parent, false)
        return ArticleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is ArticleViewHolder) {
            val item = models[position]
            holder.itemView.title_view?.text = item.title
            holder.itemView.section_view?.text = item.section
            holder.itemView.time_view?.text = DateTimeUtils.getHappenedTime(
                    DateTimeUtils.getLocalDateTime(item.lastModified), App.instance)

            val imageView = holder.itemView.image_view
            val context = imageView.context

            val imageSize = context.resources.getDimensionPixelSize(R.dimen.article_item_image_size)
            Glide.with(context)
                    .load(item.thumbnail)
                    .apply(RequestOptions()
                            .placeholder(R.drawable.bg_image_placeholder)
                            .transform(RectangleTransformation(imageSize, imageSize)))
                    .into(imageView)

            holder.itemView.setOnClickListener {
                listener?.onArticleClicked(item, imageView)
            }
        }
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener {

        fun onArticleClicked(article: Article, imageView: ImageView)
    }
}