package com.example.wishlistapp.ui

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wishlistapp.R

/**
 * Composable function that displays the app bar.
 * Provides a title and an optional back navigation button.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarView(
    title: String, // Title to display in the app bar
    onBackNavClicked: () -> Unit = {} // Callback function for back navigation
) {
    // Define the navigation icon (back button) based on the title
    val navigationIcon: @Composable () -> Unit = {
        if (title != (stringResource(id = R.string.app_name))) {
            // Show back button if the title is not App Name
            IconButton(onClick = onBackNavClicked) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        } else {
            // Don't show back button on the home screen App Name title
            null
        }
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
        navigationIcon = navigationIcon // Set the navigation icon
    )
}