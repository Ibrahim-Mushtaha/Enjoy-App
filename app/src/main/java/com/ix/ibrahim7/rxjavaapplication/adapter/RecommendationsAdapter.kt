package com.ix.ibrahim7.rxjavaapplication.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.model.Movie.Content
import kotlinx.android.synthetic.main.item_pupuler.view.*
import kotlinx.android.synthetic.main.item_pupuler.view.tv_image
import kotlinx.android.synthetic.main.item_pupuler.view.tv_title
import com.ix.ibrahim7.rxjavaapplication.util.Constant
import com.ix.ibrahim7.rxjavaapplication.util.Constant.IMAGE_URL
import com.ix.ibrahim7.rxjavaapplication.util.Constant.setImage


class RecommendationsAdapter(
    var data: ArrayList<Content>, val itemclick: onClick
) :
        RecyclerView.Adapter<RecommendationsAdapter.ViewHolder>() {

    private var on_attach = true
    var DURATION: Long = 350
    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_pupuler, parent, false)
            )
    }

    override fun getItemCount(): Int {
        return data.size
    }




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = data[position]


        holder.itemView.apply {

                Constant.setAnimation(this, position,on_attach,DURATION)
                setImage(
                    context,
                    IMAGE_URL + currentItem.posterPath,
                    tv_image,
                    Color.TRANSPARENT
                )
                tv_title.text = currentItem.title
                tv_price.text = currentItem.id.toString()

            setOnClickListener {
                itemclick.onClickItem(data[position],position,1)
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
