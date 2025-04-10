package com.example.kotlinbookshelfapiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.kotlinbookshelfapiapp.ui.screens.BookShelfApp
import com.example.kotlinbookshelfapiapp.ui.theme.KotlinBookShelfApiAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinBookShelfApiAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BookShelfApp()
                }
            }
        }
    }
}