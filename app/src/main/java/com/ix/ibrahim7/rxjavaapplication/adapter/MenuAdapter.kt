package com.ix.ibrahim7.rxjavaapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.databinding.ItemMenuBinding
import com.ix.ibrahim7.rxjavaapplication.model.MenuItem
import kotlinx.android.synthetic.main.item_menu.view.*
import java.util.ArrayList


class MenuAdapter(var data: ArrayList<MenuItem>, val itemclick: onClick) :
        RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {


    class MenuViewHolder(val mbinding: ItemMenuBinding) : RecyclerView.ViewHolder(mbinding.root) {

        fun bind(menuitem : MenuItem) {
            mbinding.apply {
                executePendingBindings()
                item = menuitem
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView_layout: ItemMenuBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_menu, parent, false
        )
        return MenuViewHolder(itemView_layout)
    }

    override fun getItemCount()= data.size


    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        val currentItem = data[position]
        holder.bind(currentItem)

        holder.itemView.apply {
            textView2.text = currentItem.title
            if (currentItem.isSelected)
                item_menu_selected.visibility=View.VISIBLE
            else
                item_menu_selected.visibility=View.INVISIBLE

            setOnClickListener {
                itemclick.onClickListener(currentItem,holder.adapterPosition)
            }
        }


    }

    interface onClick {
        fun onClickListener(menuitem: MenuItem,position: Int)
    }

}
