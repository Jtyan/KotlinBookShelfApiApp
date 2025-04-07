package com.example.kotlinbookshelfapiapp.data.repository

import android.util.Log

import com.example.kotlinbookshelfapiapp.data.model.BooksApiResponse
import com.example.kotlinbookshelfapiapp.data.model.ImageLinks
import com.example.kotlinbookshelfapiapp.domain.model.toHttps
import com.example.kotlinbookshelfapiapp.data.network.BooksApiService
import com.example.kotlinbookshelfapiapp.domain.model.BookModel

interface BooksRepository {
    suspend fun getBooks(query: String): BooksApiResponse
}

class BooksRepositoryImpl(
    private val apiService: BooksApiService
) : BooksRepository {
    override suspend fun getBooks(query: String): BooksApiResponse {
        return apiService.getBooks(query)
    }
}

fun BooksApiResponse.toBookModels(): List<BookModel> {
    val books = items?.map { bookVolume ->
        BookModel(
            id = bookVolume.id,
            title = bookVolume.volumeInfo.title,
            authors = bookVolume.volumeInfo.authors.orEmpty(),
            publishedDate = bookVolume.volumeInfo.publishedDate ?: "Unknown Date",
            categories = bookVolume.volumeInfo.categories.orEmpty(),
            pageCount = bookVolume.volumeInfo.pageCount ?: 0,
            image = ImageLinks(
                smallThumbnail = bookVolume.volumeInfo.imageLinks?.smallThumbnail?.toHttps() ?: "",
                thumbnail = bookVolume.volumeInfo.imageLinks?.thumbnail?.toHttps() ?: ""
            ),
        )
    }
    Log.d("BooksApiResponse", "toBookModels: $books")
    return books.orEmpty()
}