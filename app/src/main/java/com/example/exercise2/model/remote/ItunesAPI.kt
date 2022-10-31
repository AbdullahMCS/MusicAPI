package com.example.exercise2.model.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesAPI {
    @GET(ItunesNetwork.ENDPOINT)
    fun getSongs(
        @Query("term") term: String,
        @Query("amp;media") media: String = "music",
        @Query("amp;entity") entity: String = "song",
        @Query("amp;limit") limit: Int = 50
    ): Call<MusicResponse>
}