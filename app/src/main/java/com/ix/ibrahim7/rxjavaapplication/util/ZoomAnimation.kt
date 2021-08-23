package com.ix.ibrahim7.rxjavaapplication.util

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class ZoomAnimation : ViewPager2.PageTransformer {

    val MIN_SCALE = 0.70f
    val MIN_ALPHA = 0.5f

    override fun transformPage(page: View, position: Float) {
        val pagewidth = page.width
        val pageheight = page.height

        if (position < -1){
            page.alpha = 0f
        }else if (position <= 1){
            val scaleFactor = Math.max(MIN_SCALE,1 - Math.abs(position))
            val vertMargin = pageheight * (1 - scaleFactor) / 2
            val horzMargin = pagewidth * (1 - scaleFactor) / 2

            if (position < 0){
                page.translationX = horzMargin - vertMargin / 2
            }else{
                page.translationX = -horzMargin + vertMargin / 2
            }

            page.scaleX = scaleFactor
            page.scaleY = scaleFactor

            page.alpha = (MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA))

        }else{
            page.alpha = 0f
        }

    }
}