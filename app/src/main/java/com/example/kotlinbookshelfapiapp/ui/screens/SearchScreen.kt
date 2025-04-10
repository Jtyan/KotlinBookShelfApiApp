package com.example.kotlinbookshelfapiapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(
    onSearch: (String) -> Unit,
    onBackClick: () -> Unit,
    searchSuggestions: List<String>,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var active by rememberSaveable { mutableStateOf(true) }

    BookSearchBar(
        query = searchQuery,
        onQueryChange = { searchQuery = it },
        onSearch = { query ->
            active = false
            keyboardController?.hide()
            onSearch(query)
        },
        active = active,
        onActiveChange = { active = it },
        searchResults = searchSuggestions,
        onBackClick = {
            onBackClick()
        },
        modifier = modifier,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onBackClick: () -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    searchResults: List<String>,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(active) {
        if (active) {
            focusRequester.requestFocus()
        }
    }

    Box(
        modifier
            .fillMaxSize()
    ) {
        SearchBar(
            query = query,
            onQueryChange = onQueryChange,
            onSearch = { onSearch(it) },
            active = active,
            onActiveChange = onActiveChange,
            placeholder = { Text("Search Books") },
            leadingIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go Back")
                }
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear search"
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        ) {
            if (searchResults.isNotEmpty()) {
                LazyColumn {
                    items(count = searchResults.size) { index ->
                        val resultText = searchResults[index]
                        ListItem(
                            headlineContent = { Text(resultText) },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                            modifier = Modifier
                                .clickable {
                                    onSearch(resultText)
                                }
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}