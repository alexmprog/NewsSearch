package com.renovavision.newssearch.ui.common.widget.recycler

import android.content.Context
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView

class DividerItemDecoration : RecyclerView.ItemDecoration {

    private var drawTopDivider = true
    private var drawBottomDivider = true
    private var mDivider: Drawable? = null
    private var dividerColor = -1

    /**
     * Default divider will be used
     */
    constructor(context: Context) {
        val styledAttributes = context.obtainStyledAttributes(ATTRS)
        mDivider = styledAttributes.getDrawable(0)
        styledAttributes.recycle()
    }

    /**
     * Custom divider will be used
     */
    constructor(context: Context, @DrawableRes resId: Int) {
        mDivider = ContextCompat.getDrawable(context, resId)
    }

    fun setDrawTopDivider(drawTopDivider: Boolean) {
        this.drawTopDivider = drawTopDivider
    }

    fun setDrawBottomDivider(drawBottomDivider: Boolean) {
        this.drawBottomDivider = drawBottomDivider
    }

    fun setDividerColor(dividerColor: Int) {
        this.dividerColor = dividerColor
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount

        val startIndex = if (drawTopDivider || childCount == 0) 0 else 1
        val endIndex = if (drawBottomDivider || childCount == 0) childCount else childCount - 1

        for (i in startIndex until endIndex) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight

            mDivider!!.setBounds(left, top, right, bottom)
            if (dividerColor != -1) {
                mDivider!!.setColorFilter(dividerColor, PorterDuff.Mode.SRC_ATOP)
            }
            mDivider!!.draw(c)
        }
    }

    companion object {

        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}
