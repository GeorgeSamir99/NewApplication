package com.example.newapplication.search_screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newapplication.R
import com.example.newapplication.news.ArticlesList
import com.example.newapplication.news.NewsViewModel

@Composable
fun SearchScreen(viewModel: NewsViewModel = viewModel()) {
    val searchFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    LaunchedEffect (Unit){
        searchFocusRequester.requestFocus()
    }
    Scaffold { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()) {
            TextField(modifier = Modifier.padding(paddingValues)
                .fillMaxWidth()
                .focusRequester(searchFocusRequester)
                .onFocusChanged {
                    viewModel.isFocus.value = it.isFocused
                }
                .border(1.dp,MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(18.dp)),
                value = viewModel.searchQuery.value, onValueChange = { newValue->
                    viewModel.searchQuery.value = newValue },
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    cursorColor = MaterialTheme.colorScheme.onBackground,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedLabelColor = MaterialTheme.colorScheme.background
                ),
                placeholder = {
                    Text(text = stringResource(R.string.search))},
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.searchQuery()
                    }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),

                leadingIcon = {
                    Image(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                        modifier = Modifier.clickable {
                            viewModel.searchQuery() })},
                trailingIcon = {
                    if(viewModel.isFocus.value)
                    Image(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                        modifier = Modifier
                            .clickable {
                            if (viewModel.searchQuery.value.isNotEmpty()){
                                viewModel.searchQuery.value = ""
                            }else{
                                focusManager.clearFocus() }
                            })
                }
                )
            ArticlesList(viewModel.articlesList)

        }

    }

}