package com.example.wishlistapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room database class for the Wish List app.
 * Defines the database entities and provides access to the DAO.
 */
@Database(
    entities = [Wish::class], // List of database entities (tables)
    version = 1, // Database version
    exportSchema = false // Whether to export the database schema
)
abstract class WishDatabase : RoomDatabase() {

    /**
     * Provides access to the WishDao.
     * @return An instance of the WishDao.
     */
    abstract fun wishDao(): WishDao
}