package com.keygenqt.kchat.extensions

import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

fun Exception?.logging(defaultMessage: String): String {
    return this?.let { ex ->
        FirebaseCrashlytics.getInstance().recordException(ex.toThrowable())
        Timber.e(ex)
        ex.message
    } ?: defaultMessage
}

fun Exception.toThrowable(): Throwable {
    try {
        throw this
    } catch (ex: Exception) {
        return ex
    }
}