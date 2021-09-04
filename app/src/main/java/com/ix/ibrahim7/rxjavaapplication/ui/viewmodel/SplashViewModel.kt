package com.ix.ibrahim7.rxjavaapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel  @Inject constructor(
    application: Application
) : AndroidViewModel(application) {


    val liveData: LiveData<SplashState>
        get() = mutableLiveData
     val mutableLiveData = MutableLiveData<SplashState>()

    init {
        GlobalScope.launch {
            delay(4200)
            mutableLiveData.postValue(SplashState.MainActivity)
        }
    }


}

sealed class SplashState {
    object MainActivity : SplashState()
}
