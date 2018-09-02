package com.droidcba.kedditbysteps.commons

import android.util.Log

/**
 * @author juan.saravia
 */
object Logger {

    private const val TAG = "Keddit"

    /**
     * dt: Debug with Thread details.
     * Print current thread name plus given value.
     */
    fun dt(value: String) {
        Log.d(TAG, "Thread Name: ${Thread.currentThread().name} - $value")
    }
}