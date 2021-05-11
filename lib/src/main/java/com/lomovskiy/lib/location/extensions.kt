package com.lomovskiy.lib.location

import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Executor

suspend fun <T> Task<T>.await(executor: Executor, cancellationTokenSource: CancellationTokenSource? = null): T {
    if (isComplete) {
        val e = exception
        return if (e == null) {
            if (isCanceled) {
                throw CancellationException("Task $this was cancelled normally.")
            } else {
                @Suppress("UNCHECKED_CAST")
                result as T
            }
        } else {
            throw e
        }
    }

    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener(executor) {
            val e = exception
            if (e == null) {
                @Suppress("UNCHECKED_CAST")
                if (isCanceled) cont.cancel() else cont.resumeWith(Result.success(result as T))
            } else {
                cont.resumeWith(Result.failure(e))
            }
        }
        cancellationTokenSource?.let {
            cont.invokeOnCancellation {
                cancellationTokenSource.cancel()
            }
        }
    }
}
