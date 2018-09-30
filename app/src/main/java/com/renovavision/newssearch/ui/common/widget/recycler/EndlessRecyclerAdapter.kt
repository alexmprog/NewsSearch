package com.renovavision.newssearch.ui.common.widget.recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.renovavision.newssearch.R
import kotlinx.android.synthetic.main.view_item_vertical_progress.view.*
import java.util.ArrayList

abstract class EndlessRecyclerAdapter<M> @JvmOverloads constructor(models: MutableList<M>? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var models: MutableList<M> = models ?: ArrayList()
    protected var endlessItem: EndlessItem? = null
    var loadMoreListener: OnLoadMoreListener? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (endlessItem != null && endlessItem?.loadingError != true
                && endlessItem?.loadingInProgress != true && getLoadingPlaceholderSize() == position) {
            loadMoreListener?.onLoadMore()
            endlessItem?.loadingInProgress = true
        }
        if (holder is ProgressViewHolder) {
            showProgressItem(holder)
            showLoadingError(holder)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EndlessRecyclerAdapter.MODEL_ITEM -> createModelViewHolder(parent, viewType)
            EndlessRecyclerAdapter.PROGRESS_ITEM -> createProgressViewHolder(parent, viewType)
            else -> {
                EmptyViewHolder(View(parent.context))
            }
        }
    }

    override fun getItemCount(): Int {
        var itemCount = models.size
        if (endlessItem != null && itemCount > 0) {
            itemCount++
        }
        return itemCount
    }

    open fun getItemByPosition(position: Int): M? {
        return if (position >= models.size) {
            null
        } else models[position]
    }

    override fun getItemViewType(position: Int): Int {
        if (endlessItem != null && itemCount == position + 1) {
            return PROGRESS_ITEM
        }
        return MODEL_ITEM
    }

    fun setItems(models: List<M>, forceReload: Boolean = false) {
        if (this.models.isEmpty() || forceReload) {
            this.models = ArrayList()
            this.models.addAll(models)
        } else {
            notifyItemRemoved(itemCount - 1)
            this.models.addAll(models)
        }
        notifyDataSetChanged()
    }

    fun addItem(item: M) {
        models.add(0, item)
        notifyItemInserted(0)
    }

    open fun updateItem(item: M) {
        val indexOf = models.indexOf(item)
        if (indexOf != -1) {
            models[indexOf] = item
            notifyItemChanged(indexOf)
        }
    }

    open fun removeItem(item: M) {
        val indexOf = models.indexOf(item)
        if (indexOf != -1) {
            models.removeAt(indexOf)
            notifyItemRemoved(indexOf)
        }
    }

    fun setLoaded(needLoadMore: Boolean) {
        endlessItem = if (needLoadMore) {
            EndlessItem(false, false)
        } else {
            null
        }
    }

    fun setLoadedError() {
        if (itemCount >= 1) {
            endlessItem?.loadingInProgress = false
            endlessItem?.loadingError = true
            notifyItemChanged(itemCount - 1)
        }
    }

    protected open fun getLoadingPlaceholderSize(): Int {
        return itemCount - 1
    }

    protected open fun showProgressItem(holder: ProgressViewHolder) {
        holder.itemView.progress?.visibility = if (endlessItem?.loadingInProgress == true) View.VISIBLE else View.INVISIBLE
        holder.itemView.retry_button?.visibility = if (endlessItem?.loadingError == true) View.VISIBLE else View.INVISIBLE
    }

    protected open fun showLoadingError(holder: ProgressViewHolder) {
        holder.itemView.retry_button?.setOnClickListener {
            endlessItem?.let {
                endlessItem?.loadingInProgress = true
                endlessItem?.loadingError = false
                loadMoreListener?.onLoadMore()
                showProgressItem(holder)
            }
        }
    }

    class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    protected open fun createProgressViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_item_vertical_progress, parent, false);
        return ProgressViewHolder(itemView)
    }

    abstract fun createModelViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    companion object {

        val MODEL_ITEM = 1

        val PROGRESS_ITEM = 2
    }
}