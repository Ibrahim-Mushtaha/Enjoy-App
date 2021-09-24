package com.ix.ibrahim7.rxjavaapplication.model.video


import com.google.gson.annotations.SerializedName

data class MovieVideo(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val results: List<Result>?
)