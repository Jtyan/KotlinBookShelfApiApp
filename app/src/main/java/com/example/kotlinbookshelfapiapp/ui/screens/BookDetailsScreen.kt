package com.example.kotlinbookshelfapiapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kotlinbookshelfapiapp.BookDetailsState
import com.example.kotlinbookshelfapiapp.R
import com.example.kotlinbookshelfapiapp.domain.model.BookModel

@Composable
fun BookDetailsScreen(
    bookDetailsState: BookDetailsState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (bookDetailsState) {
        is BookDetailsState.Loading -> LoadingIndicator()
        is BookDetailsState.Error -> ErrorScreen(retryAction = {})
        is BookDetailsState.Success -> BookDetailContent(
            book = bookDetailsState.book,
            onBackClick = onBackClick,
            modifier = modifier
        )
    }
}

@Composable
fun BookDetailContent(
    book: BookModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.image.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = "Book cover for ${book.title}",
                contentScale = ContentScale.FillHeight,
                error = painterResource(R.drawable.bookplaceholder),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(vertical = 16.dp)
            )
            IconButton(
                onClick = onBackClick,
                modifier = modifier
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp)
        ) {

            BookInfo(bookInfo = book)

            BookDescription(description = book.description)

            if (book.categories.isNotEmpty()) {
                BookCategories(categories = book.categories)
            }
        }
    }
}

@Composable
fun BookInfo(
    bookInfo: BookModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        // Title
        Text(
            text = bookInfo.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )

        // Authors
        if (bookInfo.authors.isNotEmpty()) {
            Text(
                text = "By ${bookInfo.authors.joinToString(", ")}",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Published Date
            Text(
                text = "Published: ${bookInfo.publishedDate}",
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Box(
                contentAlignment = Alignment.Center
            ) {
                // Page Count
                Icon(
                    painter = painterResource(R.drawable.ic_book_pages),
                    contentDescription = "Page Count",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f),
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    text = bookInfo.pageCount.toString(),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun BookDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(top = 16.dp)
    ) {
        Text(
            text = "About this book",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            AnnotatedString.fromHtml(htmlString = description),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun BookCategories(
    categories: List<String>,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Categories: ${categories.joinToString(", ")}",
        fontSize = 14.sp,
        color = Color(0xFF3F51B5),
        modifier = modifier.padding(bottom = 8.dp)
    )
}

