package com.keygenqt.kchat.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.keygenqt.kchat.data.dao.DaoChatModel
import com.keygenqt.kchat.data.models.ChatModel

@Database(
    entities = [
        ChatModel::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoChatModel(): DaoChatModel
}
