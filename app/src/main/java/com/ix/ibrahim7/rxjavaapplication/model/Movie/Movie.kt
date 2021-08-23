package com.ix.ibrahim7.rxjavaapplication.model.Movie


import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val contents: ArrayList<Content>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)