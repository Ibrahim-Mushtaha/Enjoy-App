package com.ix.ibrahim7.rxjavaapplication.network

import com.ix.ibrahim7.rxjavaapplication.model.details.MovieDetails
import com.ix.ibrahim7.rxjavaapplication.model.movie.Movie
import com.ix.ibrahim7.rxjavaapplication.model.review.Reviews
import com.ix.ibrahim7.rxjavaapplication.model.video.MovieVideo
import retrofit2.http.*
import com.ix.ibrahim7.rxjavaapplication.util.Constant.API_KEY
import retrofit2.Response

interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopular(
            @Query("page")
            pageNumber: Int = 1,
            @Query("language")
            language: String = "en-US",
            @Query("api_key")
            apiKey: String = API_KEY
    ): Response<Movie>


    @GET("discover/movie")
    suspend fun getDiscoverMovie(
        @Query("page")
        pageNumber: Int = 1,
        @Query("language")
        language: String = "en-US",
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<Movie>

    @GET("movie/upcoming")
    suspend fun getUpComing(
            @Query("page")
            pageNumber: Int = 1,
            @Query("language")
            language: String = "en-US",
            @Query("api_key")
            apiKey: String = API_KEY
    ): Response<Movie>

    @GET("genre/movie/list")
    suspend fun getMovieList(
            @Query("language")
            language: String = "en-US",
            @Query("api_key")
            apiKey: String = API_KEY
    ): Response<Movie>

   @GET("discover/tv")
    suspend fun getTvShowList(
            @Query("language")
            language: String = "en-US",
            @Query("api_key")
            apiKey: String = API_KEY
    ): Response<Movie>

    @GET("movie/top_rated")
    suspend fun getTopRated(
            @Query("page")
            pageNumber: Int = 1,
            @Query("language")
            language: String = "en-US",
            @Query("api_key")
            apiKey: String = API_KEY
    ): Response<Movie>

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
            @Query("page")
            pageNumber: Int = 1,
            @Query("language")
            language: String = "en-US",
            @Query("api_key")
            apiKey: String = API_KEY
    ): Response<Movie>

    @GET("trending/movie/day")
    suspend fun getTrendingMovie(
            @Query("page")
            pageNumber: Int = 1,
            @Query("api_key")
            apiKey: String = API_KEY,
            @Query("language")
            language: String = "en-US"
    ): Response<Movie>


    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id")
        movie_id: Int,
        @Query("language")
        language: String = "en-US",
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<MovieDetails>



    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id")
        movie_id: Int,
        @Query("page")
        pageNumber: Int = 1,
        @Query("language")
        language: String = "en-US",
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<Reviews>


    @GET("movie/{movie_id}/recommendations")
    suspend fun getMovieRecommendations(
        @Path("movie_id")
        movie_id: Int,
        @Query("page")
        pageNumber: Int = 1,
        @Query("language")
        language: String = "en-US",
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<Movie>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimillerMovie(
        @Path("movie_id")
        movie_id: Int,
        @Query("page")
        pageNumber: Int = 1,
        @Query("language")
        language: String = "en-US",
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<Movie>


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
    ): Response<Movie>


    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideo(
        @Path("movie_id")
        movieID: String,
        @Query("language")
        language: String = "en-US",
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<MovieVideo>


}
