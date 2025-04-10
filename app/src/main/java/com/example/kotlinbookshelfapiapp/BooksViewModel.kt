package com.example.kotlinbookshelfapiapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kotlinbookshelfapiapp.data.repository.BooksRepository
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.example.kotlinbookshelfapiapp.domain.model.BookModel
import com.example.kotlinbookshelfapiapp.data.repository.toBookModels
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BooksUiState {
    data class Success(val books: List<BookModel>) : BooksUiState
    data class Error(val message: String) : BooksUiState
    object Loading : BooksUiState
}

// New state for book details
sealed interface BookDetailsState {
    data class Success(val book: BookModel) : BookDetailsState
    data class Error(val message: String) : BookDetailsState
    object Loading : BookDetailsState
}

class BooksViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {
    var booksUiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    var bookDetailsState: BookDetailsState by mutableStateOf(BookDetailsState.Loading)
        private set

    var fictionBooksState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    var thrillerBooksState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    var biographyBooksState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    var popularBooksState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    val searchSuggestions = listOf("Science Fiction", "Cooking", "Programming", "History", "Crime")

    init {
       searchBooks("")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch {
            booksUiState = BooksUiState.Loading
            booksUiState = try {
                BooksUiState.Success(booksRepository.getBooks(query).toBookModels())
            }  catch (e: IOException) {
                Log.e("BooksViewModel", "IOException: ${e.message}")
                BooksUiState.Error("Network error: ${e.message}")
            } catch (e: HttpException) {
                Log.e("BooksViewModel", "HttpException: ${e.message}")
                BooksUiState.Error("HTTP error: ${e.message}")
            } catch (e: Exception) {
                Log.e("BooksViewModel", "Exceptionn: ${e.message}")
                BooksUiState.Error("Unknown error: ${e.message ?: "Something went wrong"}")
            }
        }
    }

    fun getBookDetails(volumeId: String) {
        viewModelScope.launch {
            bookDetailsState = BookDetailsState.Loading
            bookDetailsState = try {
                BookDetailsState.Success(booksRepository.getBookById(volumeId))
            } catch (e: Exception) {
                BookDetailsState.Error("Error loading book details: ${e.message}")
            }
        }
    }

    fun getFictionBooks() {
        viewModelScope.launch {
            fictionBooksState = BooksUiState.Loading
            fictionBooksState = try {
                BooksUiState.Success(booksRepository.getFictionBooks().toBookModels())
            } catch (e: Exception) {
                BooksUiState.Error("Error loading fiction books: ${e.message}")
            }
        }
    }

    fun getThrillerBooks() {
        viewModelScope.launch {
            thrillerBooksState = BooksUiState.Loading
            thrillerBooksState = try {
                BooksUiState.Success(booksRepository.getThrillerBooks().toBookModels())
            } catch (e: Exception) {
                BooksUiState.Error("Error loading fantasy books: ${e.message}")
            }
        }
    }

    fun getBiographyBooks() {
        viewModelScope.launch {
            biographyBooksState = BooksUiState.Loading
            biographyBooksState = try {
                BooksUiState.Success(booksRepository.getBiographyBooks().toBookModels())
            } catch (e: Exception) {
                BooksUiState.Error("Error loading biography books: ${e.message}")
            }
        }
    }

    fun getPopularBooks() {
        viewModelScope.launch {
            popularBooksState = BooksUiState.Loading
            popularBooksState = try {
                BooksUiState.Success(booksRepository.getPopularBooks().toBookModels())
            } catch (e: Exception) {
                BooksUiState.Error("Error loading popular books: ${e.message}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BooksApplication)
                val booksRepository = application.appContainer.booksRepository
                BooksViewModel(booksRepository = booksRepository)
            }
        }
    }
}