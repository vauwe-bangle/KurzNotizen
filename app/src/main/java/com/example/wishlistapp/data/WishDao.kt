package com.example.wishlistapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for interacting with the Wish database.
 */
@Dao
abstract class WishDao {

    /**
     * Retrieves all wishes from the database.
     * @return A Flow emitting a list of all wishes.
     */
    @Query("SELECT * FROM `wish-table`")
    abstract fun getAllWishes(): Flow<List<Wish>>

    /**
     * Retrieves a wish by its ID.
     * @param id The ID of the wish to retrieve.
     * @return A Flow emitting the wish with the given ID.
     */
    @Query("SELECT * FROM `wish-table` WHERE id = :id")
    abstract fun getWishById(id: Long): Flow<Wish>

    /**
     * Adds a new wish to the database.
     * If a wish with the same ID already exists, it will be ignored.
     * @param wishEntity The wish to add.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addWish(wishEntity: Wish)

    /**
     * Updates an existing wish in the database.
     * @param wishEntity The wish to update.
     */
    @Update
    abstract suspend fun updateWish(wishEntity: Wish)

    /**
     * Deletes a wish from the database.
     * @param wishEntity The wish to delete.
     */
    @Delete
    abstract suspend fun deleteWish(wishEntity: Wish)
}