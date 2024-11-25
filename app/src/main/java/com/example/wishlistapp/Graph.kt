package com.example.wishlistapp

import android.content.Context
import androidx.room.Room
import com.example.wishlistapp.data.WishDatabase
import com.example.wishlistapp.data.WishRepository

/**
 * Object responsible for providing dependencies for the app.
 * Initializes the database and provides access to the repository.
 */
object Graph {

    /**
     * Instance of the WishDatabase.
     * Initialized in the `provide()` function.
     */
    lateinit var database: WishDatabase

    /**
     * Instance of the WishRepository.
     * Lazily initialized using the `database` instance.
     */
    val wishRepository by lazy {
        WishRepository(
            wishDao = database.wishDao()
        )
    }

    /**
     * Initializes the database and provides dependencies.
     * Should be called once during app initialization.
     * @param context The application context.
     */
    fun provide(context: Context) {
        database = Room.databaseBuilder(context, WishDatabase::class.java, "wish.db").build()
    }
}