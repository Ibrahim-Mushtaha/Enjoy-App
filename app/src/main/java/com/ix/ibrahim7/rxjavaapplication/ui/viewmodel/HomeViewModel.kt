package com.ix.ibrahim7.rxjavaapplication.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ix.ibrahim7.rxjavaapplication.model.Movie.Movie
import com.ix.ibrahim7.rxjavaapplication.repository.ApiRepository
import com.ix.ibrahim7.rxjavaapplication.util.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.Schedulers.io
import java.util.concurrent.TimeUnit

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ApiRepository()
    val dataPupularLiveData = MutableLiveData<Resource<Movie>>()
    val dataUpcomingLiveData = MutableLiveData<Resource<Movie>>()
    private val compositeDisposable = CompositeDisposable()
    var data: Movie? = null

    var arrayList = ArrayList<Movie>()
    var page = 1

    fun getPupular() {
        dataPupularLiveData.postValue(Resource.Loading())
     val observable = repository.getPupular(page)
         .subscribeOn(Schedulers.io())
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe(
              {c->
               /*   page++
                  if (data == null){
                      data = c*/
                      dataPupularLiveData.postValue(Resource.Success(c))
                      Log.e("eee dataPupular",data.toString())
/*                  }else{
                      val oldData = c
                      oldData!!.results!!.addAll(c.results!!)
                      dataPupularLiveData.postValue(Resource.Success(oldData))
                      Log.e("eee data2",oldData.toString())
                  }*/

              },
              {x->
                  dataPupularLiveData.postValue(Resource.Error(x.message.toString(),null))
                  Log.e("eee onError", x?.message.toString())
              })

          compositeDisposable.add(observable)
      }


    fun getUpcoming() {
        dataUpcomingLiveData.postValue(Resource.Loading())
        val observable = repository.getUpcoming(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {c->
                  /*  if (page == 1){
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
                    }*/
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
    }


    init {
        getPupular()
        getUpcoming()
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }



}