package com.ix.ibrahim7.rxjavaapplication.model.review


import com.google.gson.annotations.SerializedName

data class Reviews(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val contents: List<Content>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)