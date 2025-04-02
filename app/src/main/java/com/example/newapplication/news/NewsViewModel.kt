package com.example.newapplication.news

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.newapplication.api.ApiManger
import com.example.newapplication.api.ArticlesItem
import com.example.newapplication.api.NewsResponse
import com.example.newapplication.api.Source
import com.example.newapplication.api.SourcesResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel :ViewModel()  {
    val sourcesListStates = mutableStateListOf<Source>()
    val errorState = mutableStateOf("")
    val sourceIdState = mutableStateOf("")
    val articlesList = mutableStateListOf<ArticlesItem>()
    val isLoading = mutableStateOf(false)
    val searchQuery = mutableStateOf("")
    val isFocus = mutableStateOf(true)
    fun getSources(endpoint:String){
        ApiManger.getNewsServices().getSources(category = endpoint)
            .enqueue(object: Callback<SourcesResponse> {
                override fun onResponse(call : Call<SourcesResponse>, response: Response<SourcesResponse>) {
                    if(response.isSuccessful){
                        val list = response.body()?.sources
                        if (list?.isNotEmpty() == true)
                            sourcesListStates.addAll(list.filterNotNull())
                    }else{
                        val json = response.errorBody()?.string()
                        val gson = Gson()
                        val sources =try {
                            gson.fromJson(json,SourcesResponse::class.java)

                        } catch (e : Exception){
                            null
                        }
                        errorState.value = sources?.message ?: "ُError"
                    }
                }
                override fun onFailure(call: Call<SourcesResponse>, throwable: Throwable) {
                    errorState.value =(throwable.message ?: "")
                    Log.e("Failure " ,"${throwable.message}")
                }
            })
    }

    fun getArticlesBySource(){
        if (sourceIdState.value.isNotEmpty()){
            isLoading.value = true
        ApiManger.getNewsServices().getNewsBySources(sources = sourceIdState.value)
            .enqueue(object: Callback<NewsResponse>{
                override fun onResponse(p0: Call<NewsResponse>, response: Response<NewsResponse>) {

                    if(response.isSuccessful){
                        val list = response.body()?.articles
                        if (list?.isNotEmpty() == true){
                            isLoading.value = false
                            articlesList.clear()
                            articlesList.addAll(list.filterNotNull())
                        }
                    }else{
                        val json = response.errorBody()?.string()
                        val gson = Gson()
                        val news = gson.fromJson(json,NewsResponse::class.java)
                        errorState.value = "${news.message}"
                    }

                }

                override fun onFailure(p0: Call<NewsResponse>, throwable: Throwable) {
                    isLoading.value = false
                    errorState.value =(throwable.message ?: "")
                }
            })
    }}
    fun searchQuery(){
        isLoading.value = true
            ApiManger.getNewsServices().searchByTitle(searchQuery.value)
                .enqueue(object: Callback<NewsResponse>{
                    override fun onResponse(p0: Call<NewsResponse>, response: Response<NewsResponse>) {

                        if(response.isSuccessful){
                            val list = response.body()?.articles
                            if (list?.isNotEmpty() == true){
                                isLoading.value = false
                                articlesList.clear()
                                articlesList.addAll(list.filterNotNull())
                            }
                        }else{
                            val json = response.errorBody()?.string()
                            val gson = Gson()
                            val news = gson.fromJson(json,NewsResponse::class.java)
                            errorState.value = "${news.message}"
                        }

                    }

                    override fun onFailure(p0: Call<NewsResponse>, throwable: Throwable) {
                        isLoading.value = false
                        errorState.value =(throwable.message ?: "")
                    }
                })
        }





}