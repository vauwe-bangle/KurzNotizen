package com.example.wishlistapp.data

import android.util.Log
import kotlinx.coroutines.flow.Flow

/**
 * Repository class for managing Wish data.
 * Provides functions for accessing and manipulating wish data.
 */
class WishRepository(private val wishDao: WishDao) {

    /**
     * Retrieves all wishes from the database.
     * @return A Flow emitting a list of all wishes.
     */
    fun getAllWishes(): Flow<List<Wish>> {
        return wishDao.getAllWishes()
    }

    /**
     * Retrieves a wish by its ID.
     * @param id The ID of the wish to retrieve.
     * @return A Flow emitting the wish with the given ID.
     */
    fun getWishById(id: Long): Flow<Wish> {
        return wishDao.getWishById(id)
    }

    /**
     * Adds a new wish to the database.
     * @param wish The wish to add.
     * @throws WishRepositoryException If an error occurs during the addition.
     */
    suspend fun addWish(wish: Wish) {
        try {
            wishDao.addWish(wish)
        } catch (e: Exception) {
            // Log the exception for debugging
            Log.e("WishRepository", "Failed to add wish", e)
            // Re-throw the exception or throw a custom exception
            throw WishRepositoryException("Failed to add wish", e)
        }
    }

    /**
     * Updates an existing wish in the database.
     * @param wish The wish to update.
     * @throws WishRepositoryException If an error occurs during the update.
     */
    suspend fun updateWish(wish: Wish) {
        try {
            wishDao.updateWish(wish)
        } catch (e: Exception) {
            // Log the exception for debugging
            Log.e("WishRepository", "Failed to update wish", e)
            // Re-throw the exception or throw a custom exception
            throw WishRepositoryException("Failed to update wish", e)
        }
    }

    /**
     * Deletes a wish from the database.
     * @param wish The wish to delete.
     * @throws WishRepositoryException If an error occurs during the deletion.
     */
    suspend fun deleteWish(wish: Wish) {
        try {
            wishDao.deleteWish(wish)
        } catch (e: Exception) {
            // Log the exception for debugging
            Log.e("WishRepository", "Failed to delete wish", e)
            // Re-throw the exception or throw a custom exception
            throw WishRepositoryException("Failed to delete wish", e)
        }
    }
}

/**
 * Custom exception class for WishRepository errors.
 */
class WishRepositoryException(message: String, cause: Throwable? = null) : Exception(message, cause)