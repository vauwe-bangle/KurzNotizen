package com.example.wishlistapp.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wishlistapp.WishListViewModel

/**
 * Composable function that sets up the navigation for the app.
 * Uses Jetpack Compose Navigation to define the navigation graph and routes.
 */
@Composable
fun Navigation(
    viewModel: WishListViewModel = viewModel(), // Provides the ViewModel instance
    navController: NavHostController = rememberNavController(), // Creates or remembers the NavController
    innerPadding: PaddingValues // Padding values from the parent composable (e.g., Scaffold)
) {
    NavHost(
        navController = navController,// The NavController to manage navigation
        startDestination = Screen.HomeScreen.route // The initial screen to display
    ) {
        // Define the routes and their corresponding composables
        composable(route = Screen.HomeScreen.route) {
            // Route for the Home screen
            HomeView(navController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.AddScreen.route + "/{id}", // Route for the Add/Edit screen with an optional ID argument
            arguments = listOf(navArgument("id") {
                type = NavType.LongType // Type of the ID argument
                defaultValue = 0L // Default value if ID is not provided
                nullable = false // Whether the ID argument can be null
            })
        ) { entry ->
            val id = if (entry.arguments != null) entry.arguments!!.getLong("id") else 0L
            // Extract the ID argument from the NavBackStackEntry
            AddEditDetailView(id = id, viewModel = viewModel, navController = navController)
        }
    }
}