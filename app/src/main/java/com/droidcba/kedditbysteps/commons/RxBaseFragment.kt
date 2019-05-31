package com.droidcba.kedditbysteps.commons

import android.os.Bundle
import android.support.v4.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class RxBaseFragment : Fragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    protected lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}