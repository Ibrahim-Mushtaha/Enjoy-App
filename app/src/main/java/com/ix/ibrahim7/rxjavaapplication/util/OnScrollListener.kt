package com.ix.ibrahim7.rxjavaapplication.util

import android.util.Log
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OnScrollListener(
    var isLoading: Boolean,
    var isLastPage: Boolean,
    var totalCount: Int,
    val onComplete: () -> Unit
) : RecyclerView.OnScrollListener() {
    private val TAG = "OnScrollListener"
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val isNotLoadingAndNotLastPage = !isLoading && !isLastPage

        val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
        val isNotAtBeginning = firstVisibleItemPosition >= 0
        val isTotalMoreThenVisible = totalItemCount >=40

        val shouldPaginate = isNotLoadingAndNotLastPage
                && isAtLastItem
                && isNotAtBeginning && isTotalMoreThenVisible

        if (shouldPaginate) {
            onComplete()
        }
        Log.e("eee totalItemCount12" ,totalItemCount.toString())
        Log.e("eee totalItemCount" ,shouldPaginate.toString())
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
         onComplete()
            Log.e("eee newState" ,newState.toString())
        }
    }

}