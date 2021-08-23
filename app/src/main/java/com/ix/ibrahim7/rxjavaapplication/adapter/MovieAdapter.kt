package com.ix.ibrahim7.rxjavaapplication.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.model.Movie.Content
import com.ix.ibrahim7.rxjavaapplication.util.Constant
import com.ix.ibrahim7.rxjavaapplication.util.Constant.DURATION
import com.ix.ibrahim7.rxjavaapplication.util.Constant.IMAGE_URL
import com.ix.ibrahim7.rxjavaapplication.util.Constant.on_attach
import com.ix.ibrahim7.rxjavaapplication.util.Constant.setImage
import kotlinx.android.synthetic.main.item_all_list.view.*
import kotlinx.android.synthetic.main.item_image_slider.view.*
import kotlinx.android.synthetic.main.item_pupuler.view.*
import kotlinx.android.synthetic.main.item_pupuler.view.tv_image
import kotlinx.android.synthetic.main.item_pupuler.view.tv_title
import kotlinx.android.synthetic.main.item_upcoming.view.*
import kotlinx.android.synthetic.main.item_upcoming.view.tv_description
import kotlinx.android.synthetic.main.item_upcoming.view.tv_release_day



class MovieAdapter(
    var data: ArrayList<Content>, val type :Int, val itemclick: onClick
) :
        RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when (type) {
            1 -> {
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_pupuler, parent, false)
                )
            }
            2 -> {
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_upcoming, parent, false)
                )
            }
            3 -> {
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_image_slider, parent, false)
                )
            }
            else -> {
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_all_list, parent, false)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = data[position]




        holder.itemView.apply {

                when(type){
                    1->{
                        Constant.setAnimation(this, position,on_attach,DURATION)
                        setImage(
                            context,
                            IMAGE_URL + currentItem.posterPath,
                            tv_image,
                            Color.TRANSPARENT
                        )
                        tv_title.text = currentItem.title
                        tv_price.text = currentItem.id.toString()
                    }
                    2->{
                        Constant.setAnimation(this, position,on_attach,DURATION)
                        setImage(
                            context,
                            IMAGE_URL + currentItem.posterPath,
                            tv_image_upcoming,
                            Color.TRANSPARENT
                        )
                        tv_title.text = currentItem.title
                        tv_description.text = currentItem.overview.toString()
                        tv_release_day.text = currentItem.releaseDate.toString()
                    }
                    3->{
                        Constant.setAnimation(this, position,on_attach,DURATION)
                        setImage(
                            context,
                            IMAGE_URL + currentItem.posterPath,
                            tv_image_slider,
                            Color.TRANSPARENT
                        )
                    }
                    else ->{
                        Constant.setAnimation(this, position,on_attach,DURATION)
                        setImage(
                            context,
                            IMAGE_URL + currentItem.posterPath,
                            tv_image_list,
                            Color.TRANSPARENT
                        )
                        tv_title2.text = currentItem.title
                        tv_description2.text = currentItem.overview.toString()
                        tv_release_day2.text = currentItem.releaseDate.toString()
                    }
                }



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
