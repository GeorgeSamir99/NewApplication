package com.example.newapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newapplication.api.ApiManger
import com.example.newapplication.api.SourcesResponse
import com.example.newapplication.ui.theme.NewApplicationTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NewApplicationTheme {
                LaunchedEffect(Unit)
                {
                    getApi()
                }
            }
        }
    }
    private fun getApi(){

        ApiManger.getNewsServices().getSources()
            .enqueue(object: Callback<SourcesResponse>{
                override fun onResponse(call : Call<SourcesResponse>, response: Response<SourcesResponse>) {
                    if (response.isSuccessful){
                        Log.e("Response" ,"${response.body()}")
                    }
                    Log.e("Response" ,"${response.errorBody()?.string()}")


                }

                override fun onFailure(call: Call<SourcesResponse>, throwable: Throwable) {
                    Log.e("Failure " ,"${throwable.message}")
                }
            })
    }
}


