package com.keygenqt.kchat.base

import androidx.room.withTransaction
import com.keygenqt.kchat.data.AppDatabase

interface BaseDataService<T : Any> {

    val db: AppDatabase
    val preferences: AppSharedPreferences

    @Suppress("UNCHECKED_CAST")
    suspend fun withTransaction(body: suspend T.() -> Unit) {
        db.withTransaction {
            body.invoke(this as T)
        }
    }
}