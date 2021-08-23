package com.ix.ibrahim7.rxjavaapplication.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ix.ibrahim7.rxjavaapplication.model.Movie.Movie
import com.ix.ibrahim7.rxjavaapplication.model.details.MovieDetails
import com.ix.ibrahim7.rxjavaapplication.model.review.Reviews
import com.ix.ibrahim7.rxjavaapplication.repository.ApiRepository
import com.ix.ibrahim7.rxjavaapplication.util.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class DetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ApiRepository()
    val dataDetailsLiveData = MutableLiveData<Resource<MovieDetails>>()
    val dataReviewsLiveData = MutableLiveData<Resource<Reviews>>()
    val dataRecommendetionLiveData = MutableLiveData<Resource<Movie>>()
    val dataSimilerLiveData = MutableLiveData<Resource<Movie>>()
    private val compositeDisposable = CompositeDisposable()

    fun getMovieDetails(movieID: Int) {
        dataDetailsLiveData.postValue(Resource.Loading())
        val observable = repository.getMovieDetails(movieID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { c ->
                    dataDetailsLiveData.postValue(Resource.Success(c))
                },
                { x ->
                    dataDetailsLiveData.postValue(Resource.Error(x.message.toString(), null))
                    Log.e("eee onError", x?.message.toString())
                })

        compositeDisposable.add(observable)
    }


    fun getMovieReviews(movieID: Int) {
        dataReviewsLiveData.postValue(Resource.Loading())
        val observable = repository.getMovieReviews(movieID = movieID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { c ->
                    dataReviewsLiveData.postValue(Resource.Success(c))
                },
                { x ->
                    dataReviewsLiveData.postValue(Resource.Error(x.message.toString(), null))
                    Log.e("eee onError", x?.message.toString())
                })

        compositeDisposable.add(observable)
    }

    fun getMovieRecommendations(movieID: Int) {
        dataRecommendetionLiveData.postValue(Resource.Loading())
        val observable = repository.getMovieRecommendations(movieID = movieID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { c ->
                    dataRecommendetionLiveData.postValue(Resource.Success(c))
                },
                { x ->
                    dataRecommendetionLiveData.postValue(Resource.Error(x.message.toString(), null))
                    Log.e("eee onError", x?.message.toString())
                })

        compositeDisposable.add(observable)
    }


    fun getSimillerMovie(movieID: Int) {
        dataSimilerLiveData.postValue(Resource.Loading())
        val observable = repository.getSimillerMovie(movieID = movieID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { c ->
                    dataSimilerLiveData.postValue(Resource.Success(c))
                },
                { x ->
                    dataSimilerLiveData.postValue(Resource.Error(x.message.toString(), null))
                    Log.e("eee onError", x?.message.toString())
                })

        compositeDisposable.add(observable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


}