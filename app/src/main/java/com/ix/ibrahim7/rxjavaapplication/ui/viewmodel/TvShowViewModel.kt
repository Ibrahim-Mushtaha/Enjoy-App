package com.ix.ibrahim7.rxjavaapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ix.ibrahim7.rxjavaapplication.model.movie.Movie
import com.ix.ibrahim7.rxjavaapplication.other.getApiLang
import com.ix.ibrahim7.rxjavaapplication.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    val movieRepository: MovieRepository,
    application: Application
) : AndroidViewModel(application) {


    val dataTvShowLiveData = movieRepository.dataTvShowLiveData

    fun getTvShowList(language:String) =  movieRepository.getTvShowList(language)


    init {
        getTvShowList(application.getApiLang())
    }





}