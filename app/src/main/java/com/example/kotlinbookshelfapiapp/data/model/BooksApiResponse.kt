package com.example.kotlinbookshelfapiapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BooksApiResponse(
    val items: List<BookVolume>? = emptyList()
)

@Serializable
data class BookVolume(
    val id: String,
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val title: String,
    val authors: List<String>? = emptyList(),
    val publishedDate: String? = null,
    val categories: List<String>? = emptyList(),
    val pageCount: Int? = null,
    val imageLinks: ImageLinks? = ImageLinks("", "")
)

@Serializable
data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String
)