package com.ix.ibrahim7.rxjavaapplication.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.model.review.Content
import kotlinx.android.synthetic.main.item_review.view.*
import com.ix.ibrahim7.rxjavaapplication.util.Constant
import com.ix.ibrahim7.rxjavaapplication.util.Constant.DURATION
import com.ix.ibrahim7.rxjavaapplication.util.Constant.IMAGE_URL
import com.ix.ibrahim7.rxjavaapplication.util.Constant.on_attach
import com.ix.ibrahim7.rxjavaapplication.util.Constant.setImage


class ReviewsAdapter(
    var data: ArrayList<Content>, val itemclick: onClick
) :
    RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_review, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = data[position]


        holder.itemView.apply {

            Constant.setAnimation(this, position, on_attach, DURATION)
            setImage(
                context,
                IMAGE_URL + currentItem.authorDetails!!.avatarPath,
                tv_image_user,
                Color.TRANSPARENT
            )
            tv_review_name.text = currentItem.authorDetails.name
            tv_review_note.text = currentItem.content
            tvrating.rating = if (currentItem.authorDetails.rating!! == 0) 0f
            else
                (currentItem.authorDetails.rating / 2).toFloat()


            setOnClickListener {
                itemclick.onClickItem(data[position], position, 1)
            }
        }

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                Log.d("eee", "onScrollStateChanged: Called $newState")
                on_attach = false
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        super.onAttachedToRecyclerView(recyclerView)
    }

    interface onClick {
        fun onClickItem(content: Content, position: Int, type: Int)
    }


}
