package com.ix.ibrahim7.rxjavaapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ix.ibrahim7.rxjavaapplication.model.movie.Movie
import com.ix.ibrahim7.rxjavaapplication.other.getApiLang
import com.ix.ibrahim7.rxjavaapplication.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    val movieRepository: MovieRepository,
    application: Application
) : AndroidViewModel(application) {


    val dataDiscoverMovieLiveData = movieRepository.dataDiscoverMovieLiveData

    var pageDiscoverMovie = 1
    fun getDiscoverMovie(language:String) {
        if (pageDiscoverMovie <= 3) {
            movieRepository.getDiscoverMovie(language, page = pageDiscoverMovie)
            pageDiscoverMovie++
        }
    }


    init {
        getDiscoverMovie(application.getApiLang())
    }

}