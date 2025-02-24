package com.example.newapplication.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManger {
    private  var retrofit : Retrofit? = null
    const val API_KEY = "9a4d22cf8e74433fa10aca7c41e42bc2"
    private val httpLoggingInterceptor = HttpLoggingInterceptor{
        message->
        Log.e("API",message)
    }.apply {
        level =HttpLoggingInterceptor.Level.BODY
    }
    private val okHttpClient =OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
    private fun initRetrofit() :Retrofit {
            if (retrofit == null){
                retrofit   = Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit!!
        }

    fun getNewsServices() : NewServices{
        val newServices = initRetrofit().create(NewServices::class.java)
        return newServices
    }



}