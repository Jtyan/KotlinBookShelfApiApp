package com.example.kotlinbookshelfapiapp.model

data class BookApiResponse(
    val kind: String,
    val totalItems: String,
    val items: List<BookVolume>
)

data class BookVolume(
    val id: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>,
    val description: String,
    val publisher: String,
    val publishedDate: String,
    val pageCount: Int,
    val categories: List<String>,
    val imageLinks: ImageLinks,
    val language: String
)

data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String
)