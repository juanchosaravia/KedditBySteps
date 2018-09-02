package com.droidcba.kedditbysteps.commons

import android.support.v4.app.Fragment
import kotlinx.coroutines.experimental.Job

open class RxBaseFragment : Fragment() {

    protected var job = Job()

    override fun onResume() {
        super.onResume()
        job = Job()
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }
}