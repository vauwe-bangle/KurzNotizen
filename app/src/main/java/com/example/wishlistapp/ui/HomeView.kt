package com.example.wishlistapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlistapp.R
import com.example.wishlistapp.WishListViewModel
import com.example.wishlistapp.data.Wish


/**
 * Composable function that displays the home screen of the Wish List app.
 * Shows a list of wishes and provides options to add, edit, and delete them.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navController: NavController, // NavController for navigating between screens
    viewModel: WishListViewModel // ViewModel for managing the UI state and data operations
) {
    LocalContext.current // Access the current context

    Scaffold(topBar = {
        // App bar with the title with App's Name
        AppBarView(title = stringResource(id = R.string.app_name))
    }, floatingActionButton = {
        // Floating action button for adding a new wish
        FloatingActionButton(modifier = Modifier.padding(20.dp),
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            onClick = {
                // Navigate to the AddScreen with ID 0L (for adding a new wish)
                navController.navigate(Screen.AddScreen.route + "/0L")
            }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add_icon_desc))
        }
    }) { innerPadding ->
        val wishList = viewModel.getAllWishes.collectAsState() // Collect the list of wishes from the ViewModel
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            items(wishList.value, key = { wish -> wish.id }) { wish ->
                AnimatedVisibility(visible = true) {
                    // State for swipe-to-dismiss gesture
                    val dismissState = rememberSwipeToDismissBoxState(confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.EndToStart) {
                            // Delete the wish when swiped to the left
                            viewModel.deleteWish(wish)
                        }
                        true
                    }, positionalThreshold = { it * 0.5f })

                    SwipeToDismissBox(state = dismissState,
                        enableDismissFromStartToEnd = false, // Disable swipe-to-dismiss from right to left
                        backgroundContent = {
                            // Background content when swiping (red background with delete icon)
                            val color = animateColorAsState(
                                if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                                    Color.Red
                                } else {
                                    Color.Transparent
                                }, label = ""
                            ).value
                            val alignment = Alignment.CenterEnd
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = 20.dp), contentAlignment = alignment
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = stringResource(id = R.string.del_icon_desc),
                                    tint = Color.White
                                )

                            }
                        }) {
                        // Display the WishItem composable
                        WishItem(wish = wish) {
                            val id = wish.id
                            // Navigate to the AddScreen with the wish ID for editing
                            navController.navigate(Screen.AddScreen.route + "/$id")
                        }
                    }
                }
            }
        }
    }
}

/**
 * Composable function that displays a single wish item.
 * @param wish The wish data to display.
 * @param onClick Callback function to be invoked when the item is clicked.
 */
@Composable
fun WishItem(wish: Wish, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = wish.title,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(text = wish.description, color = MaterialTheme.colorScheme.onSecondaryContainer)
        }
    }
}