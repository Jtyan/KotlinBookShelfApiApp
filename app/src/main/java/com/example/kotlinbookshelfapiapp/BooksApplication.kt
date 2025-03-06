package com.example.kotlinbookshelfapiapp

import android.app.Application
import com.example.kotlinbookshelfapiapp.data.container.AppContainer
import com.example.kotlinbookshelfapiapp.data.container.DefaultAppContainer
import kotlin.random.Random

class BooksApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultAppContainer()
    }
}