package com.ix.ibrahim7.rxjavaapplication.other

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ix.ibrahim7.rxjavaapplication.R
import com.ix.ibrahim7.rxjavaapplication.util.Constant
import java.lang.Exception

object HolderAdapter {


    @JvmStatic
    @BindingAdapter("loadImageRec")
    fun loadImage(image: ImageView, img: Int) {
        image.setImageResource(img)
    }


    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImagePath(image: ImageView, path: String) {
            loadImage(image, Constant.IMAGE_URL + path)
    }

    @JvmStatic
    @BindingAdapter("uriImage")
    fun uriImage(image: ImageView, img: String?) {
        try {
            Glide
                .with(image.context)
                .load(img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions().transform(CenterCrop()))
                .into(image)
        } catch (e: Exception) {

        }

    }


    fun loadImage(view: ImageView, path: String) {
        try {
            Glide
                .with(view.context)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.ic_movie_placeholder)
                .apply(RequestOptions().transform(CenterCrop()))
                .into(view)
        } catch (e: Exception) {
        }
    }

}