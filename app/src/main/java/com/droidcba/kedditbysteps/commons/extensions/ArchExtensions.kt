package com.droidcba.kedditbysteps.commons.extensions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment

/**
 * Extension functions for Android Architecture components
 *
 * @author juan.saravia
 */

/**
 * Retrieve ViewModel from current Fragment.
 */
inline fun <reified T : ViewModel> Fragment.getViewModel(viewModelFactory: ViewModelProvider.Factory): T =
        ViewModelProviders.of(this, viewModelFactory)[T::class.java]