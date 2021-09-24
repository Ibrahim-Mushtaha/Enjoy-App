package com.ix.ibrahim7.rxjavaapplication.model.menu

import com.ix.ibrahim7.rxjavaapplication.other.EnumConstant

data class MenuItem(
    val code: EnumConstant =EnumConstant.POPULAR,
    val title:String,
    var isSelected:Boolean=false
)