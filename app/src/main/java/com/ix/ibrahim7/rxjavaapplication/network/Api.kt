package com.ix.ibrahim7.rxjavaapplication.network

import com.ix.ibrahim7.rxjavaapplication.model.details.MovieDetails
import com.ix.ibrahim7.rxjavaapplication.model.Movie.Movie
import com.ix.ibrahim7.rxjavaapplication.model.review.Reviews
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*
import com.ix.ibrahim7.rxjavaapplication.util.Constant.API_KEY

interface Api {



    @GET("movie/popular")
    fun getPupular(
            @Query("page")
            pageNumber: Int = 1,
            @Query("language")
            language: String = "en-US",
            @Query("api_key")
            apiKey: String = API_KEY
    ): Single<Movie>

    @GET("movie/upcoming")
    fun getUpcoming(
            @Query("page")
            pageNumber: Int = 1,
            @Query("language")
            language: String = "en-US",
            @Query("api_key")
            apiKey: String = API_KEY
    ): Observable<Movie>


    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id")
        movie_id: Int,
        @Query("language")
        language: String = "en-US",
        @Query("api_key")
        apiKey: String = API_KEY
    ): Single<MovieDetails>



    @GET("movie/{movie_id}/reviews")
    fun getMovieReviews(
        @Path("movie_id")
        movie_id: Int,
        @Query("page")
        pageNumber: Int = 1,
        @Query("language")
        language: String = "en-US",
        @Query("api_key")
        apiKey: String = API_KEY
    ): Single<Reviews>


    @GET("movie/{movie_id}/recommendations")
    fun getMovieRecommendations(
        @Path("movie_id")
        movie_id: Int,
        @Query("page")
        pageNumber: Int = 1,
        @Query("language")
        language: String = "en-US",
        @Query("api_key")
        apiKey: String = API_KEY
    ): Single<Movie>

    @GET("movie/{movie_id}/similar")
    fun getSimillerMovie(
        @Path("movie_id")
        movie_id: Int,
        @Query("page")
        pageNumber: Int = 1,
        @Query("language")
        language: String = "en-US",
        @Query("api_key")
        apiKey: String = API_KEY
    ): Single<Movie>


    @GET("search/movie")
    fun getSearch(
        @Query("page")
        pageNumber: Int = 1,
        @Query("language")
        language: String = "en-US",
        @Query("query")
        query: String,
        @Query("api_key")
        apiKey: String = API_KEY
    ): Single<Movie>


}
