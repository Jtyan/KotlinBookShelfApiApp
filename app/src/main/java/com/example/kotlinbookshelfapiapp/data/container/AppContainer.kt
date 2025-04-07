package com.example.kotlinbookshelfapiapp.data.container

import android.util.Log
import com.example.kotlinbookshelfapiapp.data.network.BooksApiService
import com.example.kotlinbookshelfapiapp.data.repository.BooksRepository
import com.example.kotlinbookshelfapiapp.data.repository.BooksRepositoryImpl
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppContainer {
    val booksRepository: BooksRepository
}

class DefaultAppContainer: AppContainer {
    private val baseUrl = "https://www.googleapis.com/books/v1/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    //logging
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitService: BooksApiService by lazy {
        try {
            retrofit.create(BooksApiService::class.java)
        } catch (e: Exception) {
            Log.e("GeneralException", "Error creating API service: ${e.message}", e)
            throw e
        }
    }

    override val booksRepository : BooksRepository by lazy {
        BooksRepositoryImpl(retrofitService)
    }
}