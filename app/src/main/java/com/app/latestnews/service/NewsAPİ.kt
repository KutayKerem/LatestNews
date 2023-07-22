package com.app.latestnews.service

import com.app.latestnews.model.mainModel
import retrofit2.http.Query
import io.reactivex.Observable
import retrofit2.http.GET

interface NewsAPİ {

    @GET("top-headlines")
    fun getNews(
        @Query("country")
        country: String,
        @Query("pageSize")
        pageSize: Int,
        @Query("apiKey")
        apiKey: String

    ) : Observable<mainModel>


    @GET("top-headlines")
    fun getCategoryNews(
        @Query("country")
        country: String,
        @Query("category")
        category: String,
        @Query("pageSize")
        pageSize: Int,
        @Query("apiKey")
        apiKey: String

    ) : Observable<mainModel>



}