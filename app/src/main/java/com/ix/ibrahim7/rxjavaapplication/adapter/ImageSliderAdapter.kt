package com.ix.ibrahim7.rxjavaapplication.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.databinding.ItemImageSliderBinding
import kotlinx.android.synthetic.main.item_image_slider.view.*


class ImageSliderAdapter(
    var data: ArrayList<Int>
) :
    RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {


    class ViewHolder(item: ItemImageSliderBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView_layout: ItemImageSliderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_image_slider, parent, false
        )
        return ViewHolder(
            itemView_layout
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.apply {
            tv_image_slider.setImageResource(data[position])
        }
    }



}
