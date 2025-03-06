package com.example.newapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.newapplication.api.ApiManger
import com.example.newapplication.api.ArticlesItem
import com.example.newapplication.api.NewsResponse
import com.example.newapplication.api.Source
import com.example.newapplication.api.SourcesResponse
import com.example.newapplication.news.HomeScreenContent
import com.example.newapplication.ui.theme.NewApplicationTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NewApplicationTheme {
                Scaffold(
                    topBar ={
                        NewsToolBar("General")
                    }
                ){  paddingValues ->
                    paddingValues
                    HomeScreenContent(modifier = Modifier.padding( paddingValues))
                }

            }
        }
    }

}

 fun getSources(
     onSuccess: (sources: List<Source>) -> Unit,
     onFailed :(massage: String) -> Unit){
    ApiManger.getNewsServices().getSources()
        .enqueue(object: Callback<SourcesResponse>{
            override fun onResponse(call : Call<SourcesResponse>, response: Response<SourcesResponse>) {
                Log.e("Response" ,"${response.body()}")
                Log.e("Response" ,"${response.errorBody()?.string()}")
                val list = response.body()?.sources
                if (list?.isNotEmpty() == true)
                    onSuccess(list.filterNotNull())

            }

            override fun onFailure(call: Call<SourcesResponse>, throwable: Throwable) {
                onFailed(throwable.message ?: "")
                Log.e("Failure " ,"${throwable.message}")
            }
        })
}

fun getArticlesBySource(
    sourceId: String,
    onSuccess: (List<ArticlesItem>) -> Unit ,
    onFailed :(massage: String) -> Unit){
    ApiManger.getNewsServices().getNewsBySources(sources = sourceId)
        .enqueue(object: Callback<NewsResponse>{
            override fun onResponse(p0: Call<NewsResponse>, response: Response<NewsResponse>) {
                val list = response.body()?.articles
                if (list?.isNotEmpty() == true){
                    onSuccess(list.filterNotNull())
                }
            }

            override fun onFailure(p0: Call<NewsResponse>, throwable: Throwable) {
                onFailed(throwable.message ?: "")
            }
        })
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsToolBar(
    title :String,
    modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        title ={
            Text( title )
        },
        navigationIcon = {
            IconButton(onClick = {})
            {
                Icon(
                   painter = painterResource(R.drawable.ic_menu_light),
                    contentDescription = "null"
                )
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.ic_search_light),
                    contentDescription = "null"
                    )

            }
        }
    )
}


@Preview
@Composable
private fun NewToolBarPreview() {
    NewsToolBar("General")
}


