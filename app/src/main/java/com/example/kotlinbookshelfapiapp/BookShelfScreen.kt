package com.example.kotlinbookshelfapiapp

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kotlinbookshelfapiapp.domain.model.BookModel

@Composable
fun BookShelfScreen(
    booksUiState: BooksUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (booksUiState) {
        is BooksUiState.Success -> BooksLazyGridScreen(
            books = booksUiState.books,
            modifier = modifier
        )
        is BooksUiState.Error -> ErrorScreen(retryAction = retryAction, modifier = modifier)
        is BooksUiState.Loading -> LoadingScreen()
    }
}

@Composable
fun BooksLazyGridScreen(
    books: List<BookModel>,
    modifier: Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 200.dp),
        state = rememberLazyGridState(),
        modifier = Modifier.padding(contentPadding)
    ) {
        items(items = books) {
            BookCard(book = it, modifier)
        }
    }
}

@Composable
fun BookCard(
    book: BookModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(1.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(book.image.smallThumbnail)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            error = painterResource(R.drawable.ic_connection_error),
            modifier = modifier.fillMaxSize().height(290.dp)
        )
        BookTitle(title = book.title)
    }
}

@Composable
fun BookTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize().height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text= title,
            fontSize = if(title.length > 40) 14.sp else 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxSize().padding(4.dp)
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = "Loading"
    )
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
