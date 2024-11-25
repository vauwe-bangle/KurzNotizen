package com.example.wishlistapp.ui

/**
 * Represents different screens in the application.
 * Each object in this sealed class defines a screen with its corresponding route.
 */
sealed class Screen(val route: String) {
    /**
     * Represents the home screen.
     */
    object HomeScreen : Screen("home_screen")

    /**
     * Represents the add screen.
     */
    object AddScreen : Screen("add_screen")
}