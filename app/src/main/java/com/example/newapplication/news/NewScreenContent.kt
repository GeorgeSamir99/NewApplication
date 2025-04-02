package com.example.newapplication.news

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.SearchRout
import com.example.newapplication.NewsToolBar
import com.example.newapplication.R
import com.example.newapplication.api.ArticlesItem
import com.example.newapplication.api.Source
import com.example.newapplication.search_screen.SearchScreen


@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeScreenContent(viewModel: NewsViewModel = viewModel(), endpointId :String,onSearchClick :()->Unit) {

    val sourcesList = viewModel.sourcesListStates

    val articlesList = viewModel.articlesList
    LaunchedEffect(Unit) {
        viewModel.getSources(endpointId)
    }

    LaunchedEffect( viewModel.sourceIdState.value){
        viewModel.getArticlesBySource()
    }

    Scaffold(
        topBar ={
            NewsToolBar(endpointId , onSearchClick = {
                onSearchClick()
            })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (sourcesList.isNotEmpty())
                SourcesTabRaw(sourcesList = sourcesList) { sourceId ->
                    articlesList.clear()
                    viewModel.sourceIdState.value = sourceId
                }
            ArticlesList(articlesList)

        }
        if (viewModel.isLoading.value)
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
            }
        if (viewModel.errorState.value.isNotEmpty()) {
            ErrorDialog(viewModel = viewModel)
        }

    }

}

@Composable
fun ErrorDialog(viewModel: NewsViewModel , modifier: Modifier = Modifier){
    val errorMessage = viewModel.errorState.value.takeIf { it.isNotEmpty() } ?: "Unknown Error"

    AlertDialog(
        onDismissRequest = {viewModel.errorState.value = ""},
        confirmButton = {
            TextButton(onClick = {viewModel.errorState.value = ""}) {
                Text(
                    text = stringResource(R.string.ok),
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 16.sp

                )
            }
        }, containerColor = MaterialTheme.colorScheme.onBackground, text = {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.background,
                fontSize = 16.sp
            )
        }
        )
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
    val showBottom = remember { mutableStateOf(false)}
    Card(onClick = {
        showBottom.value = true
    }, modifier = Modifier
        .padding(vertical = 8.dp, horizontal = 12.dp)
        .fillMaxWidth(),
        border = BorderStroke(width = 2.dp,
            color = MaterialTheme.colorScheme.onPrimary),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onPrimary,)

    ) {
        if (showBottom.value){
            ShowBottomSheet(articlesItem,{
                showBottom.value = false
            })

        }
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
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth(.5F),
            fontSize = 18.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            color = Color.Gray,
        )


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowBottomSheet(articlesItem: ArticlesItem, onDismiss:()->Unit) {
    val modelSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val context = LocalContext.current
    ModalBottomSheet(
        sheetState = modelSheetState ,
        onDismissRequest = {
          onDismiss()
        },
        containerColor =MaterialTheme.colorScheme.onBackground
    ) {
        Card(modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .fillMaxSize(),
            border = BorderStroke(width = 2.dp,
                color = MaterialTheme.colorScheme.primary),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.primary)

        ){
            AsyncImage(
                model = articlesItem.urlToImage,
                contentDescription = stringResource(R.string.news_image),
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop

            )
            Text(
                text = articlesItem.title ?: " content",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background,
                maxLines = 2,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
            )
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(articlesItem.url))
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(8.dp),

            ) {
                Text(
                    text = stringResource(R.string.view_full_article),fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,

                )
            }


        }


    }

}


@Composable
fun SourcesTabRaw(sourcesList : List<Source>,
                  onTapSelected: (sourceId : String)->Unit
) {
    val selectedIndex = remember {
        mutableIntStateOf(0)
    }
    LaunchedEffect (Unit){
        onTapSelected(sourcesList[0].id ?: "")
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

