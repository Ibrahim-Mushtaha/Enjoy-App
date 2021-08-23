package com.ix.ibrahim7.rxjavaapplication.repository

import com.ix.ibrahim7.rxjavaapplication.network.RetrofitInstance


class ApiRepository {

     fun getPupular(page: Int) =
        RetrofitInstance.api!!.getPupular(pageNumber = page)

    fun getUpcoming(page: Int) =
        RetrofitInstance.api!!.getUpcoming(pageNumber = page)


    fun getMovieDetails(movieID: Int) =
        RetrofitInstance.api!!.getMovieDetails(movie_id = movieID)


    fun getMovieReviews(movieID: Int,page: Int = 1) =
        RetrofitInstance.api!!.getMovieReviews(movie_id = movieID,pageNumber = page)

    fun getMovieRecommendations(movieID: Int,page: Int = 1) =
        RetrofitInstance.api!!.getMovieRecommendations(movie_id = movieID,pageNumber = page)


    fun getSimillerMovie(movieID: Int,page: Int = 1) =
        RetrofitInstance.api!!.getSimillerMovie(movie_id = movieID,pageNumber = page)

    fun getSearch(query: String) =
        RetrofitInstance.api!!.getSearch(query = query)


}
