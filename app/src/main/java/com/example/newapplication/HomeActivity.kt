package com.example.newapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.CategoriesScreen
import com.example.NewScreen
import com.example.SearchRout
import com.example.newapplication.categories.CategoriesScreen
import com.example.newapplication.news.HomeScreenContent
import com.example.newapplication.search_screen.SearchScreen
import com.example.newapplication.ui.theme.NewApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = CategoriesScreen) {
                    composable<CategoriesScreen> {
                        CategoriesScreen(onCategoryClick = { endpointID->
                            navController.navigate(NewScreen(endpointID))},
                            onSearchClick = {
                                navController.navigate(SearchRout)
                            })
                    }
                    composable<NewScreen> {
                        val newScreen = it.toRoute<NewScreen>()
                        HomeScreenContent(endpointId = newScreen.endpointId, onSearchClick = {
                            navController.navigate(SearchRout)
                        })
                    }

                    composable<SearchRout> {
                        SearchScreen()
                    }
                }

            }
        }


    }
}


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NewsToolBar(
        title: String,
        onSearchClick: () -> Unit,

    ) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
            title = {
                Text(title)
            },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.ic_menu_light),
                        contentDescription = "Menu"
                    )
                }
            },
            actions = {
                IconButton(onClick = onSearchClick
                ) {
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
    NewsToolBar("General", onSearchClick = {})
}



