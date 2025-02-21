package com.lanpet.core.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun ViewModel.mutexLockScope(
    mutex: Mutex,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend () -> Unit,
) {
    viewModelScope.launch(context, start) {
        try {
            mutex.withLock {
                block()
            }
        } catch (e: Exception) {
            mutex.unlock()
            throw e
        }
    }
}
