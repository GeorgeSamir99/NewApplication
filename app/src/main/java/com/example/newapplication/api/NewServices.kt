package com.example.newapplication.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewServices {

    @GET("top-headlines/sources")
    fun getSources(@Query("apiKey") apiKey : String = ApiManger.API_KEY) : Call<SourcesResponse>

}