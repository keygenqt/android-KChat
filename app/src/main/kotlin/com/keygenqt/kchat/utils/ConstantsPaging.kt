package com.keygenqt.kchat.utils

import java.util.concurrent.TimeUnit

object ConstantsPaging {

    // lists refresh once per hour
    val CACHE_TIMEOUT = TimeUnit.HOURS.toMillis(1)

    // const for api query
    const val PAGE_LIMIT = 5
}