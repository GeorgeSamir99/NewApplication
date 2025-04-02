package com.example.newapplication.categories

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.newapplication.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.NewScreen
import com.example.SearchRout
import com.example.newapplication.NewsToolBar
import com.example.newapplication.api.model.Category
import com.example.newapplication.search_screen.SearchScreen
import com.example.newapplication.ui.theme.blackWith50Opacity

@Composable
fun CategoriesScreen(onCategoryClick: (categoryID: String) -> Unit,onSearchClick :()->Unit) {
    val context = LocalContext.current
    Scaffold(
        topBar ={
            NewsToolBar("Home",onSearchClick = {
                onSearchClick()

            })
        }
    ){  paddingValues ->
    LazyColumn (modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize(),
        ){
        item{
            Text(
                text = stringResource(R.string.good_morning) +
                        stringResource(R.string.here_is_some_news_for_you),
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.W500,
                fontSize = 22.sp,
                modifier = Modifier.padding(8.dp)
            ) }

        val categoriesList = Category.categoriesList()
        items(categoriesList.size){
                index->
            CategoryCard(category = categoriesList[index],index = index, onCardClick ={
                    categoryID->
                onCategoryClick(categoryID)
                Toast.makeText(context, categoriesList[index].endpointId, Toast.LENGTH_SHORT).show()})

        }
    }
    }

}

@Preview
@Composable
private fun CategoriesScreenPreview(navController: NavController) {
    CategoriesScreen(onCategoryClick = {}, onSearchClick = {})
}


@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    index : Int,
    category: Category,
    onCardClick: ( categoryID : String)-> Unit
    ) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground, contentColor = MaterialTheme.colorScheme.background),
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
                .fillMaxWidth()
                .height(200.dp),
        onClick = { onCardClick(
            category.endpointId ?: ""
        ) }
        ){
        Row(
            modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
                if (index % 2 == 0){
                    Image(
                        painter = painterResource(category.categoryImage ?: R.drawable.ic_launcher_foreground),
                        contentDescription = stringResource(R.string.category_image),
                        Modifier.fillMaxHeight()
                            .fillMaxWidth(.45F),

                        contentScale = ContentScale.Crop

                    )

                    Column (
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = stringResource(id = category.categoryTitle ?: R.string.general),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        ViewAllRightButton()


                    }
                }else{
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = category.categoryTitle ?: R.string.general) ,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        ViewAllLeftButton()

                    }
                    Image(
                        painter = painterResource(category.categoryImage ?: R.drawable.ic_launcher_foreground),
                        contentDescription = stringResource(R.string.category_image),
                        Modifier.fillMaxWidth(.45F)
                            .fillMaxHeight()
                            .scale(2F)
                    )


                }
                }

            }

        }




@Composable
fun ViewAllRightButton() {
    Row (
        modifier = Modifier
            .padding(8.dp)
            .background(blackWith50Opacity, shape = CircleShape),
        verticalAlignment = Alignment.CenterVertically

    ){ Text(
            text = stringResource(R.string.view_all),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(4.dp)
        )
        Image(
            painter = painterResource(R.drawable.view_all_right_arrow),
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)

        )

    }
}
@Composable
fun ViewAllLeftButton(){
    Row (
        modifier = Modifier
            .padding(8.dp)
            .background(blackWith50Opacity, shape = CircleShape),
        verticalAlignment = Alignment.CenterVertically

    ){
        Image(
            painter = painterResource(R.drawable.view_all_left_arrow),
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)

        )
        Text(
            text = stringResource(R.string.view_all),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(4.dp)
        )

    }
}

@Preview
@Composable
private fun ViewALLRightButtonPreview() {
    ViewAllRightButton()
}


@Preview
@Composable
private fun CategoryCardPreview() {
    val categoriesList = Category.categoriesList()
    CategoryCard(index = 0 , category = categoriesList[0], onCardClick = {})

}


