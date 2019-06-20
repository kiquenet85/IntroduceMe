package com.kiquenet.introduceme.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * A NestedScrollView that implements the new-and-improved NestedScrollingParent2
 * interface and that defines its own customized nested scrolling behavior.
 */
class ScrollForNestedRecycler(context: Context, attributes: AttributeSet) :
    CustomNestedScrollView(context, attributes) {

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        val rv = target as RecyclerView
        if (dy < 0 && isRvScrolledToTop(rv) || dy > 0 && !isNsvScrolledToBottom(this)) {
            scrollBy(0, dy)
            consumed[1] = dy
            return
        }
        super.onNestedPreScroll(target, dx, dy, consumed, type)
    }

    // Note that we no longer need to override onNestedPreFling() here; the
    // new-and-improved nested scrolling APIs give us the nested flinging
    // behavior we want already by default!

    private fun isNsvScrolledToBottom(nsv: NestedScrollView): Boolean {
        return !nsv.canScrollVertically(1)
    }

    private fun isRvScrolledToTop(rv: RecyclerView): Boolean {
        val lm = rv.layoutManager as LinearLayoutManager?
        return lm?.findFirstVisibleItemPosition() == 0 && lm?.findViewByPosition(0)?.top == 0
    }
}