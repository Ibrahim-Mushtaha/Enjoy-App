package com.ix.ibrahim7.rxjavaapplication.di

import com.ix.ibrahim7.rxjavaapplication.ui.dialog.LoadingDialog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLoadingDialog() = LoadingDialog()

}