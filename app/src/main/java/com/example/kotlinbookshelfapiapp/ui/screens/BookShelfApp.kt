package com.example.kotlinbookshelfapiapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlinbookshelfapiapp.BooksViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun BookShelfApp() {
    Scaffold(
        modifier = Modifier.statusBarsPadding()
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            val booksViewModel: BooksViewModel = viewModel(factory = BooksViewModel.Factory)
            val navController = rememberNavController()

            LaunchedEffect(key1 = Unit) {
                booksViewModel.getFictionBooks()
                booksViewModel.getPopularBooks()
                booksViewModel.getThrillerBooks()
                booksViewModel.getBiographyBooks()
            }

            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(
                        searchBooksState = booksViewModel.booksUiState,
                        fictionBooksState = booksViewModel.fictionBooksState,
                        thrillerBooksState = booksViewModel.thrillerBooksState,
                        biographyBooksState = booksViewModel.biographyBooksState,
                        popularBooksState = booksViewModel.popularBooksState,
                        onBookClick = { bookId ->
                            booksViewModel.getBookDetails(bookId)
                            navController.navigate("book_details/$bookId")
                        },
                        onSearchClick = {
                            navController.navigate("search")
                        }
                    )
                }
                composable("book_details/{bookId}") { backStackEntry ->
                    BookDetailsScreen(
                        bookDetailsState = booksViewModel.bookDetailsState,
                        onBackClick = { navController.navigateUp() }
                    )
                }
                composable("search") {
                    SearchScreen(
                        onSearch = { query ->
                            booksViewModel.searchBooks(query)
                            navController.popBackStack()
                        },
                        onBackClick = { navController.navigateUp() },
                        searchSuggestions = booksViewModel.searchSuggestions
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookShelfTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Bookshelf",
                fontWeight = FontWeight.Light,
                fontSize = 32.sp,
                letterSpacing = 1.5.sp,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    )
}