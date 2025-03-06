package com.example.kotlinbookshelfapiapp.data.repository

import com.example.kotlinbookshelfapiapp.data.model.BookModel
import com.example.kotlinbookshelfapiapp.data.model.BooksApiResponse
import com.example.kotlinbookshelfapiapp.data.network.BooksApiService

interface BooksRepository {
    suspend fun getBooks(query: String) : BooksApiResponse
}

class BooksRepositoryImpl(
    private val apiService: BooksApiService
) : BooksRepository {
    override suspend fun getBooks(query: String): BooksApiResponse {
        return apiService.getBooks(query)
    }
}

 fun BooksApiResponse.toBookModels(): List<BookModel> {
    return items.map{bookVolume ->
         BookModel(
             id = bookVolume.id,
             title = bookVolume.volumeInfo.title,
             authors = bookVolume.volumeInfo.authors,
             publishedDate = bookVolume.volumeInfo.publishedDate,
             categories = bookVolume.volumeInfo.categories,
             pageCount = bookVolume.volumeInfo.pageCount,
             image = bookVolume.volumeInfo.imageLinks,
         )
    }
 }