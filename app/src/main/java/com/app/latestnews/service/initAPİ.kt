package com.app.latestnews.service

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object initAPİ {


   var retrofit : Retrofit? = null
   var BASE_URL = "https://newsapi.org/v2/"

    fun getRetrofit (): NewsAPİ {



        if(retrofit == null){

            retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        }
        return retrofit!!.create(NewsAPİ::class.java)

    }




}