package com.example.wishlistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.wishlistapp.ui.Navigation
import com.example.wishlistapp.ui.theme.WishListAppTheme

/**
 * Main activity of the Wish List app.
 * Sets up the UI and navigation.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable edge-to-edge display for a more immersive experience
        enableEdgeToEdge()
        setContent {
            // Apply the app's theme
            WishListAppTheme {
                // Use Scaffold to provide a basic layout structure
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        // Set up navigation within the Scaffold
                        Navigation(innerPadding = innerPadding)
                    }
                }
            }
        }
    }