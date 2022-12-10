package com.example.mvvmimagesearchapp

data class UnsplashPhoto(
    val id:String,
    val description:String?,
    val urls:UnsplashPhotoUrls,
    val user:UnsplashUser

) {

    data class UnsplashPhotoUrls(
        val raw:String,
        val full:String,
        val regular:String,
        val small:String,
        val thumb:String
    )
    data class UnsplashUser(
        val name: String,
        val userName: String
    ){
        val attributionUrl get() = "https://unsplash.com/$userName?"
    }





}