package com.ix.ibrahim7.rxjavaapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
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
    val dataTopRatedLiveData = MutableLiveData<ResultRequest<Any>>()
    val dataTrendingLiveData = MutableLiveData<ResultRequest<Any>>()

    val dataDetailsLiveData = MutableLiveData<ResultRequest<Any>>()
    val dataReviewsLiveData = MutableLiveData<ResultRequest<Any>>()
    val dataRecommendationLiveData = MutableLiveData<ResultRequest<Any>>()
    val dataSimilarLiveData = MutableLiveData<ResultRequest<Any>>()

    var data: Movie? = null

    var arrayList = ArrayList<Movie>()
    var page = 1

    fun getPopularMovie() {
        CoroutineScope(Dispatchers.IO).launch {
            dataPopularLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getPopular(page)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
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

    fun getUpComingMovie() {
        CoroutineScope(Dispatchers.IO).launch {
            dataUpcomingLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getUpComing(page)
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

    fun getTopRated() {
        CoroutineScope(Dispatchers.IO).launch {
            dataTopRatedLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getTopRated(page)
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

    fun getTrendingMovie() {
        CoroutineScope(Dispatchers.IO).launch {
            dataTrendingLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getTrendingMovie()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
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

    fun getMovieDetails(movieID: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            dataDetailsLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getMovieDetails(movieID)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            dataDetailsLiveData.postValue(ResultRequest.success(it))
                            Log.e("movieDetailsSuccess", it.toString())
                        }

                    } else {
                        Log.e("movieDetailsErrorRequest", response.errorBody().toString())
                        dataDetailsLiveData.postValue(
                            ResultRequest.error(
                                "Ooops: ${response.errorBody()}",
                                response
                            )
                        )
                    }
                } catch (e: HttpException) {
                    Log.e("movieDetailsErrorHttp", e.message().toString())
                    dataDetailsLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${e.message()}",
                            e
                        )
                    )

                } catch (t: Throwable) {
                    Log.e("movieDetailsErrorThrowable", t.message.toString())
                    dataDetailsLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${t.message}",
                            t
                        )
                    )
                }
            }
        }
    }


    fun getMovieReviews(movieID: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            dataReviewsLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getMovieReviews(movie_id = movieID)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            dataReviewsLiveData.postValue(ResultRequest.success(it))
                            Log.e("movieReviewsSuccess", it.toString())
                        }

                    } else {
                        Log.e("movieReviewsErrorRequest", response.errorBody().toString())
                        dataReviewsLiveData.postValue(
                            ResultRequest.error(
                                "Ooops: ${response.errorBody()}",
                                response
                            )
                        )
                    }
                } catch (e: HttpException) {
                    Log.e("movieReviewsErrorHttp", e.message().toString())
                    dataReviewsLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${e.message()}",
                            e
                        )
                    )

                } catch (t: Throwable) {
                    Log.e("movieReviewsErrorThrowable", t.message.toString())
                    dataReviewsLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${t.message}",
                            t
                        )
                    )
                }
            }
        }
    }

    fun getMovieRecommendations(movieID: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            dataRecommendationLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getMovieRecommendations(movie_id = movieID)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            dataRecommendationLiveData.postValue(ResultRequest.success(it))
                            Log.e("recommendationsMovieSuccess", it.toString())
                        }

                    } else {
                        Log.e("recommendationsMovieErrorRequest", response.errorBody().toString())
                        dataRecommendationLiveData.postValue(
                            ResultRequest.error(
                                "Ooops: ${response.errorBody()}",
                                response
                            )
                        )
                    }
                } catch (e: HttpException) {
                    Log.e("recommendationsMovieErrorHttp", e.message().toString())
                    dataRecommendationLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${e.message()}",
                            e
                        )
                    )

                } catch (t: Throwable) {
                    Log.e("recommendationsMovieErrorThrowable", t.message.toString())
                    dataRecommendationLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${t.message}",
                            t
                        )
                    )
                }
            }
        }
    }


    fun getSimillerMovie(movieID: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            dataSimilarLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getSimillerMovie(movie_id = movieID)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            dataSimilarLiveData.postValue(ResultRequest.success(it))
                            Log.e("simillerMovieSuccess", it.toString())
                        }

                    } else {
                        Log.e("simillerMovieErrorRequest", response.errorBody().toString())
                        dataSimilarLiveData.postValue(
                            ResultRequest.error(
                                "Ooops: ${response.errorBody()}",
                                response
                            )
                        )
                    }
                } catch (e: HttpException) {
                    Log.e("simillerMovieErrorHttp", e.message().toString())
                    dataSimilarLiveData.postValue(
                        ResultRequest.error(
                            "Ooops: ${e.message()}",
                            e
                        )
                    )

                } catch (t: Throwable) {
                    Log.e("simillerMovieErrorThrowable", t.message.toString())
                    dataSimilarLiveData.postValue(
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
