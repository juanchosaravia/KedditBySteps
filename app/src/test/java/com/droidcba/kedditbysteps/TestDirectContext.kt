package com.droidcba.kedditbysteps

import kotlinx.coroutines.experimental.CancellableContinuation
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.Delay
import java.util.concurrent.TimeUnit
import kotlin.coroutines.experimental.CoroutineContext

/**
 * I found this useful implementation in the JetBrain Forum:
 * https://discuss.kotlinlang.org/t/unit-testing-coroutines/6453/5
 */
class TestDirectContext : CoroutineDispatcher(), Delay {
    override fun scheduleResumeAfterDelay(time: Long, unit: TimeUnit, continuation: CancellableContinuation<Unit>) {
        continuation.resume(Unit)
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        block.run()
    }
}