package com.ix.ibrahim7.rxjavaapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ix.ibrahim7.rxjavaapplication.repository.MovieRepository
import com.ix.ibrahim7.rxjavaapplication.repository.detailsRepository
import javax.inject.Inject


class DetailsViewModel @Inject constructor(
    val detailsRepository: detailsRepository,
    val movieRepository: MovieRepository,
    application: Application
) : AndroidViewModel(application) {

    val dataDetailsLiveData = detailsRepository.dataDetailsLiveData
    val dataReviewsLiveData = detailsRepository.dataReviewsLiveData
    val dataRecommendationLiveData = detailsRepository.dataRecommendationLiveData
    val dataSimilarLiveData = detailsRepository.dataSimilarLiveData
    val dataMovieVideoLiveData = movieRepository.dataMovieVideoLiveData

    fun getMovieDetails(movieID: Int,language:String) =  detailsRepository.getMovieDetails(movieID,language)
    fun getMovieReviews(movieID: Int,language:String) =  detailsRepository.getMovieReviews(movieID,language)
    fun getMovieRecommendations(movieID: Int,language:String) =  detailsRepository.getMovieRecommendations(movieID,language)
    fun getSimilarMovie(movieID: Int, language:String) =  detailsRepository.getSimillerMovie(movieID,language)
    fun getMovieVideo(movieID: String, language:String) =  movieRepository.getMovieVideo(movieID,language)


    override fun onCleared() {
        super.onCleared()
        onCleared()
    }
}