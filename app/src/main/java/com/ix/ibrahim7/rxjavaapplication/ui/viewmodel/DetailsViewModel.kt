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

    fun getMovieDetails(movieID: Int) =  movieRepository.getMovieDetails(movieID)
    fun getMovieReviews(movieID: Int) =  movieRepository.getMovieReviews(movieID)
    fun getMovieRecommendations(movieID: Int) =  movieRepository.getMovieRecommendations(movieID)
    fun getSimillerMovie(movieID: Int) =  movieRepository.getSimillerMovie(movieID)

}