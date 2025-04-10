package com.example.kotlinbookshelfapiapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kotlinbookshelfapiapp.BooksUiState
import com.example.kotlinbookshelfapiapp.R
import com.example.kotlinbookshelfapiapp.domain.model.BookModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    searchBooksState: BooksUiState,
    fictionBooksState: BooksUiState,
    thrillerBooksState: BooksUiState,
    biographyBooksState: BooksUiState,
    popularBooksState: BooksUiState,
    onBookClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = { BookShelfTopAppBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(start = 12.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                // Search bar
                SearchBarButton(
                    onClick = onSearchClick,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Search Results (if any)
                if (searchBooksState is BooksUiState.Success && searchBooksState.books.isNotEmpty()) {
                    SectionTitle(text = "Search Results")

                    HorizontalBooksList(
                        books = searchBooksState.books,
                        onBookClick = onBookClick
                    )
                }

                // New Releases
                SectionTitle(text = "Fiction")

                when (fictionBooksState) {
                    is BooksUiState.Loading -> LoadingIndicator()
                    is BooksUiState.Error -> Text("Could not load new releases")
                    is BooksUiState.Success -> HorizontalBooksList(
                        books = fictionBooksState.books,
                        onBookClick = onBookClick
                    )
                }

                SectionTitle(text = "Thriller")

                when (thrillerBooksState) {
                    is BooksUiState.Loading -> LoadingIndicator()
                    is BooksUiState.Error -> Text("Could not load popular books")
                    is BooksUiState.Success -> HorizontalBooksList(
                        books = thrillerBooksState.books,
                        onBookClick = onBookClick
                    )
                }

                SectionTitle(text = "Biography")

                when (biographyBooksState) {
                    is BooksUiState.Loading -> LoadingIndicator()
                    is BooksUiState.Error -> Text("Could not load popular books")
                    is BooksUiState.Success -> HorizontalBooksList(
                        books = biographyBooksState.books,
                        onBookClick = onBookClick
                    )
                }

                // Popular Books
                SectionTitle(text = "Popular Books")

                when (popularBooksState) {
                    is BooksUiState.Loading -> LoadingIndicator()
                    is BooksUiState.Error -> Text("Could not load popular books")
                    is BooksUiState.Success -> HorizontalBooksList(
                        books = popularBooksState.books,
                        onBookClick = onBookClick
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = "",
        onQueryChange = {},
        onSearch = {},
        active = false,
        onActiveChange = { if (it) onClick() },
        placeholder = { Text("Search books") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 12.dp)
    ) {}
}

@Composable
fun HorizontalBooksList(
    books: List<BookModel>,
    onBookClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        items(books) { book ->
            // Use a smaller version of your existing BookCard
            SmallBookCard(
                book = book,
                onClick = { onBookClick(book.id) }
            )
        }
    }
}

@Composable
fun SectionTitle(
    text: String
) {
    Text(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, end = 12.dp)
    )
}

@Composable
fun SmallBookCard(
    book: BookModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        shape = RectangleShape,
        modifier = modifier.width(108.dp)
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(book.image.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                error = painterResource(R.drawable.bookplaceholder),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_connection_error),
            contentDescription = "Error"
        )
        Text(
            text = "Failed to load",
            modifier = Modifier.padding(4.dp)
        )
        Button(onClick = retryAction) {
            Text(text = "Retry")
        }
    }
}


