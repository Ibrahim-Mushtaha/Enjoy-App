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
    val dataTvShowLiveData = movieRepository.dataTvShowLiveData

    var pagePopularMovie = 1
    fun getPopularMovie(language:String) {
        movieRepository.getPopularMovie(language,page = pagePopularMovie)
        pagePopularMovie++
    }
    fun getUpComingMovie(language:String) =  movieRepository.getUpComingMovie(language)
    fun getNowPlaying(language:String) =  movieRepository.getNowPlaying(language)
    fun getTopRated(language:String) =  movieRepository.getTopRated(language)
    fun getTrendingMovie(language:String) =  movieRepository.getTrendingMovie(language)
    fun getTvShowList(language:String) =  movieRepository.getTvShowList(language)


/*    fun getUpcoming() {
        dataUpcomingLiveData.postValue(Resource.Loading())
        val observable = repository.getUpcoming(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {c->
                  *//*  if (page == 1){
                        data = c
                        arrayList.clear()
                        arrayList.add(data!!)
                        dataUpcomingLiveData.postValue(Resource.Success(c))
                        Log.e("eee dataUpcoming",data.toString())
                    }else{
                        val oldData = c
                        data!!.contents!!.addAll(c.contents!!)
                        arrayList.add(oldData)
                        dataUpcomingLiveData.postValue(Resource.Success(data!!))
                        Log.e("eee dataUpcoming1",oldData.toString())
                    }*//*
                    page++
                    if (data == null){
                        data = c
                     //   arrayList.clear()
                     //   arrayList.add(data!!)
                        dataUpcomingLiveData.postValue(Resource.Success(c))
                        Log.e("eee dataUpcoming",data.toString())
                    }else{
                        val oldData = c
                        data!!.contents!!.addAll(c.contents!!)
                    //    arrayList.add(oldData)
                        dataUpcomingLiveData.postValue(Resource.Success(data!!))
                        Log.e("eee dataUpcoming1",oldData.toString())
                    }

                },
                {x->
                    dataUpcomingLiveData.postValue(Resource.Error(x.message.toString(),null))
                })

        compositeDisposable.add(observable)
    }

    fun getSearch(query:String) {
        val observable =io.reactivex.rxjava3.core.Observable.create(ObservableOnSubscribe<String> { emitter ->
            emitter.onNext(query)
        })
            .subscribeOn(io())
            .doOnNext{c->
                Log.e("eee", "upstream $c")
            }
            .map {}
            .debounce(2, TimeUnit.SECONDS)
            .distinctUntilChanged()
            .subscribe{o->
                repository.getSearch(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { c ->
                            dataUpcomingLiveData.postValue(Resource.Success(c))
                        },{
                            dataUpcomingLiveData.postValue(Resource.Error(it.message.toString(),null))
                        })
                Log.e("eee", "downstrem $o")
            }

        compositeDisposable.add(observable)
    }*/


    init {
        getPopularMovie(application.getApiLang())
        getTopRated(application.getApiLang())
        getTrendingMovie(application.getApiLang())
        getTvShowList(application.getApiLang())
    }





}