package com.example.wishlistapp

import android.app.Application

/**
 * Application class for the Wish List app.
 * Initializes the app's dependencies.
 */
class WishListApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize the app's dependencies using the Graph object
        Graph.provide(this)
    }
}