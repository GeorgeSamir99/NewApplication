package com.example.newapplication.news

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.newapplication.R
import com.example.newapplication.api.ArticlesItem
import com.example.newapplication.api.Source
import com.example.newapplication.getArticlesBySource
import com.example.newapplication.getSources

@Composable
fun HomeScreenContent(modifier: Modifier = Modifier) {

    val sourcesListStates = remember {
        mutableStateListOf<Source>()
    }
    val errorState = remember {
        mutableStateOf("")
    }
    val sourceIdState = remember {
        mutableStateOf("")
    }
    val articlesList = remember {
        mutableStateListOf<ArticlesItem>()
    }
    LaunchedEffect(Unit) {
        getSources(
            onSuccess = {
                    sourcesList->
                sourcesListStates.addAll(sourcesList)
            },
            onFailed = {
                    error->
                errorState.value = error
            }
        )
    }


    LaunchedEffect (sourceIdState.value){
        getArticlesBySource(sourceIdState.value,
            onSuccess = {
                    list->

                articlesList.addAll(list)
            },
            onFailed ={
                    massage->

            }
        )
    }
//        ApiManger.getNewsServices().getNewsBySources(sourceIdState.value)
//            .enqueue(object: Callback<NewsResponse>{
//                override fun onResponse(p0: Call<NewsResponse>, response: Response<NewsResponse>) {
//                    val list = response.body()?.articles
//                    if (list?.isNotEmpty() == true){
//                        articlesList.clear()
//                        articlesList.addAll(list.filterNotNull())
//                    }
//                }
//
//                override fun onFailure(p0: Call<NewsResponse>, throwable: Throwable) {
//                }
//            })

    Column(modifier = modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        SourcesTabRaw(sourcesList = sourcesListStates ){
                sourceId->
            articlesList.clear()
            sourceIdState.value = sourceId
        }
        ArticlesList(articlesList)

    }



}

@Composable
fun  ArticlesList(articlesList : List<ArticlesItem>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(articlesList){
                articleItem->
            NewsCard(articleItem)
        }
    }

}

@Composable
fun NewsCard(articlesItem : ArticlesItem, modifier: Modifier = Modifier) {
    Card(onClick = {},
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .fillMaxWidth(),
        border = BorderStroke(width = 2.dp,
            color = Color.White),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onPrimary,)

    ) {
        AsyncImage(
            model = articlesItem.urlToImage,
            contentDescription = stringResource(R.string.news_image),
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 4.dp)
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop

        )
        Text(
            text = articlesItem.title ?: " content",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2,
            modifier = Modifier.padding(horizontal = 8.dp),
            overflow = TextOverflow.Ellipsis


        )
        Text(
            text = articlesItem.author ?: " content",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth(.5F),
            fontSize = 18.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            color = Color.Gray,
        )


    }
}




@Composable
fun SourcesTabRaw(sourcesList : List<Source>,
                  onTapSelected: (sourceId : String)->Unit
) {
    val selectedIndex = remember {
        mutableIntStateOf(0)
    }
    LazyRow {
        itemsIndexed(sourcesList){ index, sourceItem ->
            Tab(selected = index == selectedIndex.intValue,
                onClick = { selectedIndex.intValue = index
                    onTapSelected(sourceItem.id ?: "")
                },
                modifier = Modifier.padding(horizontal = 2.dp, vertical = 2.dp),
                text = {
                    Text(
                        text = sourceItem.name?: "",
                        fontSize = 18.sp,
                        textDecoration = if (index == selectedIndex.intValue) TextDecoration.Underline else TextDecoration.None,
                        //  fontWeight  = if (index == selectedIndex.intValue) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun  SourcesTabRawPreview() {
    SourcesTabRaw(listOf(
        Source(name = "ABS"),
        Source(name = "ABS"),
        Source(name = "ABS"),
    )){

    }
}

