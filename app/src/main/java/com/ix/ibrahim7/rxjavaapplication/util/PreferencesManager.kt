package com.ix.ibrahim7.rxjavaapplication.util

import android.content.Context
import android.content.SharedPreferences
import com.ix.ibrahim7.rxjavaapplication.other.PREFERENCES_NAME


class PreferencesManager(private val mContext: Context) {
    val sharedPreference: SharedPreferences =
        mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreference.edit()


    @Volatile
    private var instance: PreferencesManager? = null
    private val LOCK = Any()

    operator fun invoke(mContext: Context) =
        instance ?: synchronized(LOCK) {
            instance ?: PreferencesManager(mContext).also {
                instance = it
            }
        }

     fun createPreferencesManager(mContext: Context) = PreferencesManager(mContext)

}
