package com.example.kotlinbookshelfapiapp.data.model

data class BookModel(
    val id: String,
    val title: String,
    val authors: List<String>,
    val publishedData: String,
    val category: List<String>,
    val pageCount: Int,
    val image: ImageLinks
)
