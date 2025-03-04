package com.example.kotlinbookshelfapiapp.data.repository

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