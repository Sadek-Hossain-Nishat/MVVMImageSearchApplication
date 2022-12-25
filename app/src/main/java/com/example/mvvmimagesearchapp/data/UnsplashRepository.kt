package com.example.mvvmimagesearchapp.data

import com.example.mvvmimagesearchapp.api.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApi) {
}