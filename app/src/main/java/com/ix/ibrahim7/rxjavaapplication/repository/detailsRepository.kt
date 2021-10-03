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
class detailsRepository @Inject constructor(val movieApi: MovieApi) {

    val dataDetailsLiveData = MutableLiveData<ResultRequest<Any>>()
    val dataReviewsLiveData = MutableLiveData<ResultRequest<Any>>()
    val dataRecommendationLiveData = MutableLiveData<ResultRequest<Any>>()
    val dataSimilarLiveData = MutableLiveData<ResultRequest<Any>>()


    fun getMovieDetails(movieID: Int,language:String) {
        CoroutineScope(Dispatchers.IO).launch {
            dataDetailsLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getMovieDetails(movieID,language)
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


    fun getMovieReviews(movieID: Int,language:String) {
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

    fun getMovieRecommendations(movieID: Int,language:String) {
        CoroutineScope(Dispatchers.IO).launch {
            dataRecommendationLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getMovieRecommendations(movie_id = movieID,language = language)
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


    fun getSimillerMovie(movieID: Int,language:String) {
        CoroutineScope(Dispatchers.IO).launch {
            dataSimilarLiveData.postValue(ResultRequest.loading("loading"))
            val response = movieApi.getSimillerMovie(movie_id = movieID, language = language)
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

}
