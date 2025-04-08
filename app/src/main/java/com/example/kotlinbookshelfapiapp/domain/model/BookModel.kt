package com.example.kotlinbookshelfapiapp.domain.model

import com.example.kotlinbookshelfapiapp.data.model.ImageLinks

data class BookModel(
    val id: String,
    val title: String,
    val authors: List<String>,
    val publishedDate: String,
    val description: String,
    val categories: List<String>,
    val pageCount: Int,
    val image: ImageLinks
)

fun String.toHttps(): String {
    return this.replace("http://", "https://")
}