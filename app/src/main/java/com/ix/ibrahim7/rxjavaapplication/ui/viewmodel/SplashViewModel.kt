package com.ix.ibrahim7.rxjavaapplication.ui.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel constructor() : ViewModel() {


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
