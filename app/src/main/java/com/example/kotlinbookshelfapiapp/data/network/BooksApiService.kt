package com.example.kotlinbookshelfapiapp.data.network

import com.example.kotlinbookshelfapiapp.data.model.BookVolume
import com.example.kotlinbookshelfapiapp.data.model.BooksApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApiService {

    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 20)
    : BooksApiResponse

    @GET("volumes/{volumeId}")
    suspend fun getBookById(@Path("volumeId") volumeId: String): BookVolume

    @GET("volumes")
    suspend fun getFictionBooks(
        @Query("q") query: String = "subject:fiction",
        @Query("orderBy") orderBy: String = "newest",
        @Query("maxResults") maxResults: Int = 20
    ): BooksApiResponse

    @GET("volumes")
    suspend fun getThrillerBooks(
        @Query("q") query: String = "subject:thriller",
        @Query("orderBy") orderBy: String = "newest",
        @Query("maxResults") maxResults: Int = 20
    ): BooksApiResponse

    @GET("volumes")
    suspend fun getBiographyBooks(
        @Query("q") query: String = "subject:biography",
        @Query("orderBy") orderBy: String = "newest",
        @Query("maxResults") maxResults: Int = 20
    ): BooksApiResponse

    @GET("volumes")
    suspend fun getPopularBooks(
        @Query("q") query: String = "subject:Bestseller",
        @Query("orderBy") orderBy: String = "relevance",
        @Query("maxResults") maxResults: Int = 20
    ): BooksApiResponse
}
