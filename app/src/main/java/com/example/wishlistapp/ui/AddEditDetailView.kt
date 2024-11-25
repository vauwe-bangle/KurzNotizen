package com.example.wishlistapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlistapp.R
import com.example.wishlistapp.WishListViewModel
import com.example.wishlistapp.data.Wish
import kotlinx.coroutines.launch

/**
 * Composable function that displays the screen for adding or editing a wish.
 * Allows the user to input the wish title and description.
 */
@Composable
fun AddEditDetailView(
    id: Long, // ID of the wish to edit (0L for adding a new wish)
    viewModel: WishListViewModel, // ViewModel for managing the UI state and data operations
    navController: NavController // NavController for navigating between screens
) {
    val scope = rememberCoroutineScope() // Coroutine scope for launching coroutines
    val snackBarHostState = remember { SnackbarHostState() } // Snackbar host state for displaying snackbars
    val wishTitle by viewModel.wishTitleState.collectAsState() // Observe wish title as StateFlow
    val wishDescription by viewModel.wishDescriptionState.collectAsState() // Observe wish description as StateFlow
    var isLoading by rememberSaveable { mutableStateOf(false) } // State for loading indicator

    // LaunchedEffect to handle errors from the ViewModel
    LaunchedEffect(viewModel.errorState) {
        viewModel.errorState.collect { error ->
            if (error != null) {
                // Display error message using Snackbar
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = error.message ?: "An error occurred",
                        duration = SnackbarDuration.Short
                    )
                }
                // Reset the error state
                viewModel.resetErrorState()
            }
        }
    }
    // LaunchedEffect to fetch wish details if editing an existing wish
    if (id != 0L) {
        LaunchedEffect(key1 = id) {
            viewModel.getWishById(id = id).collect { wish ->
                viewModel.onWishTitleChange(wish.title)
                viewModel.onWishDescriptionChange(wish.description)
            }
        }
    } else {
        // Reset title and description if adding a new wish
        viewModel.onWishTitleChange("")
        viewModel.onWishDescriptionChange("")
    }
    Scaffold(topBar = {
        // App bar with title based on add/edit mode
        AppBarView(
            title = if (id == 0L) stringResource(id = R.string.add_wish) else stringResource(R.string.update_wish)
        ) { navController.navigateUp() }// Navigate back when back button is clicked
    }, snackbarHost = { SnackbarHost(snackBarHostState) } // Snackbar host for displaying messages
    ) { innerPadding ->
        val focusRequester = remember { FocusRequester() } // Focus requester for the description field
        val focusManager = LocalFocusManager.current // Focus manager for clearing focus
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Text field for wish title
            WishTextField(label = stringResource(id = R.string.label_title),
                value = wishTitle,
                onValueChanged = { viewModel.onWishTitleChange(it) },
                keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),
                isValid = { it.isNotBlank() } // Validate title is not blank
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Text field for wish description
            WishTextField(label = stringResource(id = R.string.label_description),
                value = wishDescription,
                onValueChanged = { viewModel.onWishDescriptionChange(it) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier.focusRequester(focusRequester),
                isValid = { it.isNotBlank() } // Validate description is not blank
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Button to add or update the wish
            Button(onClick = {
                scope.launch {
                    isLoading = true // Show progress indicator immediately
                    try {
                        // Determine the message based on add/edit mode and field validation
                        val message =
                            if (viewModel.wishTitleState.value.isNotEmpty() && viewModel.wishDescriptionState.value.isNotEmpty()) {
                                if (id != 0L) {
                                    // Update existing wish
                                    viewModel.updateWish(
                                        Wish(
                                            id = id,
                                            title = viewModel.wishTitleState.value.trim(),
                                            description = viewModel.wishDescriptionState.value.trim()
                                        )
                                    )
                                    "Wish has been updated"
                                } else {
                                    // Add new wish
                                    viewModel.addWish(
                                        Wish(
                                            title = viewModel.wishTitleState.value.trim(),
                                            description = viewModel.wishDescriptionState.value.trim()
                                        )
                                    )
                                    "Wish has been created"
                                }
                            } else {
                                "Enter fields to create a wish"
                            }
                        // Show Snackbar with the message and navigate back
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = message,
                                actionLabel = "Dismiss",
                                duration = SnackbarDuration.Short
                            )
                            navController.navigateUp()
                        }
                    } finally {
                        isLoading = false // Hide progress indicator
                    }
                }
            }) {
                ShowProgressOrText(isLoading, id) // Display progress indicator or button text
            }
        }
    }
}

@Composable
fun ShowProgressOrText(isLoading: Boolean, id: Long) {
    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.size(24.dp))
    } else {
        Text(text = if (id == 0L) stringResource(id = R.string.add_wish) else stringResource(id = R.string.update_wish))
    }
}

@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier,
    isValid: (String) -> Boolean = { true }
) {
    fun isValidTitle(title: String): Boolean {
        return title.isNotBlank() && title.length <= 50 // Maximum title length
    }

    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    OutlinedTextField(value = value,
        onValueChange = {
            onValueChanged(it)
            if (!isValidTitle(it)) {
                isError = true
                errorMessage = "Title must be between 1 and 50 characters"
            } else {
                isError = false
                errorMessage = ""
            }
        },
        label = {
            Text(text = label)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        isError = isError
    )
    if (isError) {
        Text(
            text = errorMessage,
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}