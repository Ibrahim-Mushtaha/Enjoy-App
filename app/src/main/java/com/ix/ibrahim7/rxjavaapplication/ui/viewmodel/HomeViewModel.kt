package com.ix.ibrahim7.rxjavaapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ix.ibrahim7.rxjavaapplication.model.movie.Movie
import com.ix.ibrahim7.rxjavaapplication.other.getApiLang
import com.ix.ibrahim7.rxjavaapplication.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val movieRepository: MovieRepository,
    application: Application
) : AndroidViewModel(application) {


    val dataPopularLiveData = movieRepository.dataPopularLiveData
    val dataUpcomingLiveData = movieRepository.dataUpcomingLiveData
    val dataNowPlayingLiveData = movieRepository.dataNowPlayingLiveData
    val dataTopRatedLiveData = movieRepository.dataTopRatedLiveData
    val dataTrendingLiveData = movieRepository.dataTrendingLiveData
    val dataMovieVideoLiveData = movieRepository.dataMovieVideoLiveData

    var pagePopularMovie = 1
    var pageTrendingMovie = 1
    fun getPopularMovie(language:String) {
        movieRepository.getPopularMovie(language,page = pagePopularMovie)
        pagePopularMovie++
    }

    fun getUpComingMovie(language:String) =  movieRepository.getUpComingMovie(language)
    fun getNowPlaying(language:String) =  movieRepository.getNowPlaying(language)
    fun getTopRated(language:String) =  movieRepository.getTopRated(language)
    fun getMovieVideo(movieID: String, language:String) =  movieRepository.getMovieVideo(movieID,language)
    fun getTrendingMovie(language:String) {
        movieRepository.getTrendingMovie(language,page = pageTrendingMovie)
        pageTrendingMovie++
    }

    init {
        getPopularMovie(application.getApiLang())
        getTopRated(application.getApiLang())
        getTrendingMovie(application.getApiLang())
    }


    override fun onCleared() {
        super.onCleared()
        onCleared()
    }


}