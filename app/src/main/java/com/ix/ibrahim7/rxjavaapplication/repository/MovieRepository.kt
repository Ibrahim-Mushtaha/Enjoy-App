package com.ix.ibrahim7.rxjavaapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ix.ibrahim7.rxjavaapplication.model.movie.Content
import com.ix.ibrahim7.rxjavaapplication.model.movie.Movie
import com.ix.ibrahim7.rxjavaapplication.network.MovieApi
import com.ix.ibrahim7.rxjavaapplication.util.ResultRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(val movieApi: MovieApi) {

    val dataPopularLiveData = MutableLiveData<ResultRequest<Any>>()
    val dataUpcomingLiveData = MutableLiveData<ResultRequest<Any>>()
    val dataNowPlayingLiveData = MutableLiveData<ResultRequest<Any>>()
    val dataTopRatedLiveData = MutableLiveData<ResultRequest<Any>>()
    val dataTrendingLiveData = MutableLiveData<ResultRequest<Any>>()
    val dataDiscoverMovieLiveData = MutableLiveData<ResultRequest<Any>>()
    val dataTvShowLiveData = MutableLiveData<ResultRequest<Any>>()
    val dataMovieVideoLiveData = MutableLiveData<ResultRequest<Any>>()
    private val dataPopularMovie = arrayListOf<Content>()
    private val dataDiscoverMovie = arrayListOf<Content>()
    private val dataTrendingMovie = arrayListOf<Content>()

    fun getPopularMovie(language:String, page: Int) {
        if (page == 1)
            dataPopularMovie.clear()
        CoroutineScope(Dispatchers.IO).launch {
            dataPopularLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getPopular(page,language)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (dataPopularMovie.size > 0)
                                dataPopularMovie.addAll(dataPopularMovie.size - 1, it.contents!!)
                            else
                                dataPopularMovie.addAll(it.contents!!)
                            it.contents = dataPopularMovie
                            dataPopularLiveData.postValue(ResultRequest.success(it))
                            Log.e("popularMovieSuccess", it.toString())
                        }
                    } else {
                        Log.e("popularMovieErrorRequest", response.errorBody().toString())
                        dataPopularLiveData.postValue(
                            ResultRequest.error(
                                "Ooops: ${response.errorBody()}",
                                response
                            )
                        )
                    }
                } catch (e: HttpException) {
                    Log.e("popularMovieeErrorHttp", e.message().toString())
                    dataPopularLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${e.message()}",
                            e
                        )
                    )

                } catch (t: Throwable) {
                    Log.e("popularMovieErrorThrowable", t.message.toString())
                    dataPopularLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${t.message}",
                            t
                        )
                    )
                }
            }
        }
    }


    fun getDiscoverMovie(language:String, page: Int) {
        if (page == 1)
            dataDiscoverMovie.clear()
        CoroutineScope(Dispatchers.IO).launch {
            dataDiscoverMovieLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getDiscoverMovie(page,language)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (dataDiscoverMovie.size > 0)
                                dataDiscoverMovie.addAll(dataDiscoverMovie.size - 1, it.contents!!)
                            else
                                dataDiscoverMovie.addAll(it.contents!!)
                            it.contents = dataDiscoverMovie
                            dataDiscoverMovieLiveData.postValue(ResultRequest.success(it))
                            Log.e("discoverMovieSuccess", it.toString())
                        }
                    } else {
                        Log.e("discoverMovieErrorRequest", response.errorBody().toString())
                        dataDiscoverMovieLiveData.postValue(
                            ResultRequest.error(
                                "Ooops: ${response.errorBody()}",
                                response
                            )
                        )
                    }
                } catch (e: HttpException) {
                    Log.e("discoverMovieErrorHttp", e.message().toString())
                    dataDiscoverMovieLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${e.message()}",
                            e
                        )
                    )

                } catch (t: Throwable) {
                    Log.e("discoverMovieErrorThrowable", t.message.toString())
                    dataDiscoverMovieLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${t.message}",
                            t
                        )
                    )
                }
            }
        }
    }

    fun getMovieVideo(movieID:String,language:String) {
        CoroutineScope(Dispatchers.IO).launch {
            dataMovieVideoLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getMovieVideo(movieID,language)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            dataMovieVideoLiveData.postValue(ResultRequest.success(it))
                            Log.e("movieVideoSuccess", it.toString())
                        }

                    } else {
                        Log.e("movieVideoErrorRequest", response.errorBody().toString())
                        dataMovieVideoLiveData.postValue(
                            ResultRequest.error(
                                "Ooops: ${response.errorBody()}",
                                response
                            )
                        )
                    }
                } catch (e: HttpException) {
                    Log.e("movieVideoErrorHttp", e.message().toString())
                    dataMovieVideoLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${e.message()}",
                            e
                        )
                    )

                } catch (t: Throwable) {
                    Log.e("movieVideoErrorThrowable", t.message.toString())
                    dataMovieVideoLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${t.message}",
                            t
                        )
                    )
                }
            }
        }
    }

    fun getUpComingMovie(language:String) {
        CoroutineScope(Dispatchers.IO).launch {
            dataUpcomingLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getUpComing(1,language)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            dataUpcomingLiveData.postValue(ResultRequest.success(it))
                            Log.e("upComingMovieSuccess", it.toString())
                        }

                    } else {
                        Log.e("upComingMovieErrorRequest", response.errorBody().toString())
                        dataUpcomingLiveData.postValue(
                            ResultRequest.error(
                                "Ooops: ${response.errorBody()}",
                                response
                            )
                        )
                    }
                } catch (e: HttpException) {
                    Log.e("upComingMovieErrorHttp", e.message().toString())
                    dataUpcomingLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${e.message()}",
                            e
                        )
                    )

                } catch (t: Throwable) {
                    Log.e("upComingMovieErrorThrowable", t.message.toString())
                    dataUpcomingLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${t.message}",
                            t
                        )
                    )
                }
            }
        }
    }

    fun getNowPlaying(language:String) {
        CoroutineScope(Dispatchers.IO).launch {
            dataNowPlayingLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getNowPlaying(1,language)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            dataNowPlayingLiveData.postValue(ResultRequest.success(it))
                            Log.e("nowPlayingMovieSuccess", it.toString())
                        }

                    } else {
                        Log.e("nowPlayingMovieErrorRequest", response.errorBody().toString())
                        dataNowPlayingLiveData.postValue(
                            ResultRequest.error(
                                "Ooops: ${response.errorBody()}",
                                response
                            )
                        )
                    }
                } catch (e: HttpException) {
                    Log.e("nowPlayingMovieErrorHttp", e.message().toString())
                    dataNowPlayingLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${e.message()}",
                            e
                        )
                    )

                } catch (t: Throwable) {
                    Log.e("nowPlayingMovieErrorThrowable", t.message.toString())
                    dataNowPlayingLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${t.message}",
                            t
                        )
                    )
                }
            }
        }
    }

    fun getTopRated(language:String) {
        CoroutineScope(Dispatchers.IO).launch {
            dataTopRatedLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getTopRated(1,language)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            dataTopRatedLiveData.postValue(ResultRequest.success(it))
                            Log.e("topRatedMovieSuccess", it.toString())
                        }

                    } else {
                        Log.e("topRatedMovieErrorRequest", response.errorBody().toString())
                        dataTopRatedLiveData.postValue(
                            ResultRequest.error(
                                "Ooops: ${response.errorBody()}",
                                response
                            )
                        )
                    }
                } catch (e: HttpException) {
                    Log.e("topRatedMovieErrorHttp", e.message().toString())
                    dataTopRatedLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${e.message()}",
                            e
                        )
                    )

                } catch (t: Throwable) {
                    Log.e("topRatedMovieErrorThrowable", t.message.toString())
                    dataTopRatedLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${t.message}",
                            t
                        )
                    )
                }
            }
        }
    }

    fun getTrendingMovie(language:String,page: Int) {
        if (page == 1)
            dataTrendingMovie.clear()
        CoroutineScope(Dispatchers.IO).launch {
            dataTrendingLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getTrendingMovie(language = language,pageNumber = page)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (dataTrendingMovie.size > 0)
                                dataTrendingMovie.addAll(dataTrendingMovie.size - 1, it.contents!!)
                            else
                                dataTrendingMovie.addAll(it.contents!!)
                            it.contents = dataTrendingMovie
                            dataTrendingLiveData.postValue(ResultRequest.success(it))
                            Log.e("trendingMovieSuccess", it.toString())
                        }

                    } else {
                        Log.e("trendingMovieErrorRequest", response.errorBody().toString())
                        dataTrendingLiveData.postValue(
                            ResultRequest.error(
                                "Ooops: ${response.errorBody()}",
                                response
                            )
                        )
                    }
                } catch (e: HttpException) {
                    Log.e("trendingMovieErrorHttp", e.message().toString())
                    dataTrendingLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${e.message()}",
                            e
                        )
                    )

                } catch (t: Throwable) {
                    Log.e("trendingMovieErrorThrowable", t.message.toString())
                    dataTrendingLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${t.message}",
                            t
                        )
                    )
                }
            }
        }
    }


    fun getTvShowList(language:String) {
        CoroutineScope(Dispatchers.IO).launch {
            dataTvShowLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getTvShowList(language = language)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            dataTvShowLiveData.postValue(ResultRequest.success(it))
                            Log.e("tvShowSuccess", it.toString())
                        }

                    } else {
                        Log.e("tvShowErrorRequest", response.errorBody().toString())
                        dataTvShowLiveData.postValue(
                            ResultRequest.error(
                                "Ooops: ${response.errorBody()}",
                                response
                            )
                        )
                    }
                } catch (e: HttpException) {
                    Log.e("tvShowErrorHttp", e.message().toString())
                    dataTvShowLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${e.message()}",
                            e
                        )
                    )

                } catch (t: Throwable) {
                    Log.e("tvShowErrorThrowable", t.message.toString())
                    dataTvShowLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${t.message}",
                            t
                        )
                    )
                }
            }
        }
    }


    fun getSearch(query: String) =
        movieApi.getSearch(query = query)


}
