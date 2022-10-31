package com.example.exercise2.model.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ItunesNetwork {

    const val BASE_URL = "https://itunes.apple.com/"
    const val ENDPOINT = "search"

    val itunesApi: ItunesAPI by lazy {
        initRetrofit().create( ItunesAPI::class.java )
    }

    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}