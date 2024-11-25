package com.example.wishlistapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishlistapp.data.Wish
import com.example.wishlistapp.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for managing the UI state and data operations for the Wish List screen.
 */
class WishListViewModel(
    private val wishRepository: WishRepository = Graph.wishRepository
) : ViewModel() {
    /**
     * StateFlow for the wish title input field.
     */
    private val _wishTitleState = MutableStateFlow("")
    val wishTitleState: StateFlow<String> = _wishTitleState.asStateFlow()

    /**
     * StateFlow for the wish description input field.
     */
    private val _wishDescriptionState = MutableStateFlow("")
    val wishDescriptionState: StateFlow<String> = _wishDescriptionState.asStateFlow()

    /**
     * StateFlow for emitting errors that occur during data operations.
     */
    private val _errorState = MutableStateFlow<Throwable?>(null)
    val errorState: StateFlow<Throwable?> = _errorState.asStateFlow()

    /**
     * StateFlow for emitting the list of all wishes.
     * Uses `stateIn` to collect the flow from the repository and keep it in memory
     * while the ViewModel is active.
     */
    val getAllWishes: StateFlow<List<Wish>> = wishRepository.getAllWishes().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), emptyList()
    )

    /**
     * Adds a new wish to the database.
     * @param wish The wish to add.
     */
    fun addWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                wishRepository.addWish(wish)
            } catch (e: Exception) {
                _errorState.value = e
            }
        }
    }

    /**
     * Updates an existing wish in the database.
     * @param wish The wish to update.
     */
    fun updateWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                wishRepository.updateWish(wish)
            } catch (e: Exception) {
                _errorState.value = e
            }
        }
    }

    /**
     * Deletes a wish from the database.
     * @param wish The wish to delete.
     */
    fun deleteWish(wish: Wish) {
        viewModelScope.launch {
            try {
                wishRepository.deleteWish(wish)
            } catch (e: Exception) {
                _errorState.value = e
            }
        }
    }

    /**
     * Retrieves a wish by its ID.
     * @param id The ID of the wish to retrieve.
     * @return A Flow emitting the wish with the given ID.
     */
    fun getWishById(id: Long): Flow<Wish> {
        return wishRepository.getWishById(id = id)
    }

    /**
     * Updates the wish title state.
     * @param newString The new title string.
     */
    fun onWishTitleChange(newString: String) {
        _wishTitleState.value = newString
    }

    /**
     * Updates the wish description state.
     * @param newString The new description string.
     */
    fun onWishDescriptionChange(newString: String) {
        _wishDescriptionState.value = newString
    }

    /**
     * Resets the error state to null.
     */
    fun resetErrorState() {
        _errorState.value = null
    }
}