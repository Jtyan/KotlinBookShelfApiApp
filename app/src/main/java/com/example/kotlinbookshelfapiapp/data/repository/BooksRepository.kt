package com.example.kotlinbookshelfapiapp.data.repository

import com.example.kotlinbookshelfapiapp.data.model.BookVolume

import com.example.kotlinbookshelfapiapp.data.model.BooksApiResponse
import com.example.kotlinbookshelfapiapp.data.model.ImageLinks
import com.example.kotlinbookshelfapiapp.domain.model.toHttps
import com.example.kotlinbookshelfapiapp.data.network.BooksApiService
import com.example.kotlinbookshelfapiapp.domain.model.BookModel

interface BooksRepository {
    suspend fun getBooks(query: String): BooksApiResponse
    suspend fun getBookById(volumeId: String): BookModel
    suspend fun getFictionBooks(): BooksApiResponse
    suspend fun getThrillerBooks(): BooksApiResponse
    suspend fun getBiographyBooks(): BooksApiResponse
    suspend fun getPopularBooks(): BooksApiResponse
}

class BooksRepositoryImpl(
    private val apiService: BooksApiService
) : BooksRepository {
    override suspend fun getBooks(query: String): BooksApiResponse {
        return apiService.getBooks(query)
    }

    override suspend fun getBookById(volumeId: String): BookModel {
        val bookVolume = apiService.getBookById(volumeId)
        return bookVolume.toBookModel()
    }

    override suspend fun getFictionBooks(): BooksApiResponse {
        return apiService.getFictionBooks()
    }

    override suspend fun getThrillerBooks(): BooksApiResponse {
        return apiService.getThrillerBooks()
    }

    override suspend fun getBiographyBooks(): BooksApiResponse {
        return apiService.getBiographyBooks()
    }

    override suspend fun getPopularBooks(): BooksApiResponse {
        return apiService.getPopularBooks()
    }
}

fun BooksApiResponse.toBookModels(): List<BookModel> {
    return items?.map { bookVolume ->
        bookVolume.toBookModel()
    } ?: emptyList()
}

fun BookVolume.toBookModel(): BookModel {
    return BookModel(
        id = this.id,
        title = this.volumeInfo.title,
        authors = this.volumeInfo.authors.orEmpty(),
        publishedDate = this.volumeInfo.publishedDate ?: "Unknown Date",
        description = this.volumeInfo.description ?: "No description available",
        categories = this.volumeInfo.categories.orEmpty(),
        pageCount = this.volumeInfo.pageCount ?: 0,
        image = ImageLinks(
            smallThumbnail = this.volumeInfo.imageLinks?.smallThumbnail?.toHttps() ?: "",
            thumbnail = this.volumeInfo.imageLinks?.thumbnail?.toHttps() ?: ""
        )
    )
}