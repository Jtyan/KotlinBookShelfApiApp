package com.example.kotlinbookshelfapiapp.data.network

import com.example.kotlinbookshelfapiapp.data.model.BooksApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApiService {

    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 20)
    : BooksApiResponse
}