package com.renovavision.newssearch.ui.common.widget.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

class EmptyRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        RecyclerView(context, attrs, defStyleAttr) {

    private var progressView: View? = null
    private var emptyView: View? = null
    private var showEmptyViewIfAdapterNotSet = false

    private val observer = object : RecyclerView.AdapterDataObserver() {

        override fun onChanged() {
            updateState()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            updateState()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            updateState()
        }

        private fun updateState() {
            checkState()
        }
    }

    val isEmptyViewShown: Boolean
        get() = emptyView?.visibility == View.VISIBLE

    private fun checkState() {
        if (adapter == null && showEmptyViewIfAdapterNotSet) {
            emptyView?.visibility = View.VISIBLE
            progressView?.visibility = View.GONE
            visibility = View.GONE
        } else if (adapter != null) {
            val emptyViewVisible = adapter.itemCount == 0
            emptyView?.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
            visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
            progressView?.visibility = View.GONE
        }
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(observer)
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(observer)
    }

    fun setProgressView(progressView: View) {
        this.progressView = progressView
    }

    fun showProgressView() {
        this.progressView?.visibility = View.VISIBLE
        this.emptyView?.visibility = View.GONE
        visibility = View.GONE
    }

    fun hideProgressView() {
        this.progressView?.visibility = View.GONE
        checkState()
    }

    fun setEmptyView(emptyView: View, showIfAdapterNotSet: Boolean) {
        this.emptyView = emptyView
        this.showEmptyViewIfAdapterNotSet = showIfAdapterNotSet
    }

    fun showEmptyView() {
        this.progressView?.visibility = View.GONE
        this.emptyView?.visibility = View.VISIBLE
        visibility = View.GONE
    }
}