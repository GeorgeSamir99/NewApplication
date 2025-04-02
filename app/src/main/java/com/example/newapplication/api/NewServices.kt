package com.example.newapplication.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewServices {

    @GET("top-headlines/sources")
    fun getSources(
        @Query("category" )category :String,
        @Query("apiKey") apiKey : String = ApiManger.API_KEY) : Call<SourcesResponse>

    @GET("everything")
    fun getNewsBySources(
        @Query("sources")sources: String,
        @Query("apiKey") apiKey : String = ApiManger.API_KEY) : Call<NewsResponse>
    @GET("everything")
    fun searchByTitle(
        @Query("q") q : String,
        @Query("apiKey") apiKey : String = ApiManger.API_KEY) : Call<NewsResponse>

}