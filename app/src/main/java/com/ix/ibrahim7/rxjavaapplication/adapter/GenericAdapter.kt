package com.ix.ibrahim7.rxjavaapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.model.movie.Content
import com.ix.ibrahim7.rxjavaapplication.util.Constant
import kotlinx.android.synthetic.main.item_full_movie_width.view.*
import kotlinx.android.synthetic.main.item_main_movie.view.*

class GenericAdapter<T>(
    @LayoutRes val layoutId: Int,
    var type: Int,
    val itemclick: OnListItemViewClickListener<T>
) :
    RecyclerView.Adapter<GenericAdapter.GenericViewHolder<T>>() {

    private var onAttach = true
    private var inflater: LayoutInflater? = null


    val diffCallback = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    var data: List<T>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        val layoutInflater = inflater ?: LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layoutId, parent, false)
        return GenericViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        val itemViewModel = differ.currentList[position]
        holder.bind(itemViewModel, type)
        holder.itemView.apply {
        Constant.setAnimation(this, position, Constant.on_attach, Constant.DURATION)

            when(itemViewModel){
                is Content ->{
                   when(layoutId){
                       R.layout.item_main_movie->{
                           val movieRate = (itemViewModel.voteAverage!! / 2).toFloat()
                           tvRating.text = movieRate.toString()
                           movieRating.rating = movieRate
                       }
                       R.layout.item_full_movie_width->{
                           val movieRate = (itemViewModel.voteAverage!! / 2).toFloat()
                           tvRatingUpComing.text = movieRate.toString()
                           movieRatingUpComing.rating = movieRate
                       }
                   }
                }
            }
            setOnClickListener {
                itemclick.onClickItem(itemViewModel,1)
            }
        }
    }


    class GenericViewHolder<T>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemViewModel: T, F: Int) {
            binding.setVariable(F, itemViewModel)
            binding.executePendingBindings()
        }

    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                onAttach = false
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        super.onAttachedToRecyclerView(recyclerView)
    }

    interface OnListItemViewClickListener<T> {
        fun onClickItem(itemViewModel: T, type: Int)
    }

}