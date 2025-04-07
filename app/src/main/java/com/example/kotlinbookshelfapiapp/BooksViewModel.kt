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

class BooksViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {
    var booksUiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    init {
       searchBooks("Colors")
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

    fun retryAction(query: String = "Colors") {
        searchBooks(query)
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