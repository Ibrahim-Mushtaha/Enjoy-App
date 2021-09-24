package com.ix.ibrahim7.rxjavaapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ix.ibrahim7.rxjavaapplication.repository.MovieRepository
import javax.inject.Inject


class DetailsViewModel @Inject constructor(
    val movieRepository: MovieRepository,
    application: Application
) : AndroidViewModel(application) {

    val dataDetailsLiveData = movieRepository.dataDetailsLiveData
    val dataReviewsLiveData = movieRepository.dataReviewsLiveData
    val dataRecommendationLiveData = movieRepository.dataRecommendationLiveData
    val dataSimilarLiveData = movieRepository.dataSimilarLiveData
    val dataMovieVideoLiveData = movieRepository.dataMovieVideoLiveData

    fun getMovieDetails(movieID: Int,language:String) =  movieRepository.getMovieDetails(movieID,language)
    fun getMovieReviews(movieID: Int,language:String) =  movieRepository.getMovieReviews(movieID,language)
    fun getMovieRecommendations(movieID: Int,language:String) =  movieRepository.getMovieRecommendations(movieID,language)
    fun getSimilarMovie(movieID: Int, language:String) =  movieRepository.getSimillerMovie(movieID,language)
    fun getMovieVideo(movieID: String, language:String) =  movieRepository.getMovieVideo(movieID,language)

}