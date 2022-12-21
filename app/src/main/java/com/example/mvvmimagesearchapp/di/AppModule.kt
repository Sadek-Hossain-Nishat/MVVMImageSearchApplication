package com.example.mvvmimagesearchapp.di

import com.example.mvvmimagesearchapp.api.UnsplashApi
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object AppModule {

    @Provides
    fun provideRetrofit():Retrofit=
        Retrofit.Builder()
            .baseUrl(UnsplashApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}