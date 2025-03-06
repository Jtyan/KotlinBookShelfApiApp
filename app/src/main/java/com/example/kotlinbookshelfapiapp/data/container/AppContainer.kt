package com.example.kotlinbookshelfapiapp.data.container

import com.example.kotlinbookshelfapiapp.data.network.BooksApiService
import com.example.kotlinbookshelfapiapp.data.repository.BooksRepository
import com.example.kotlinbookshelfapiapp.data.repository.BooksRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create

interface AppContainer {
    val booksRepository: BooksRepository
}

class DefaultAppContainer: AppContainer {
    private val baseUrl = "https://www.googleapis.com/books/v1"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: BooksApiService by lazy {
        retrofit.create(BooksApiService::class.java)
    }

    override val booksRepository : BooksRepository by lazy {
        BooksRepositoryImpl(retrofitService)
    }
}