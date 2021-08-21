package com.keygenqt.kchat.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.keygenqt.kchat.data.models.ChatModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoChatModel {
    @Query("SELECT * FROM ChatModel ORDER BY id DESC")
    fun pagingSource(): PagingSource<Int, ChatModel>

    @Query("SELECT * FROM ChatModel WHERE id = :id")
    fun getModel(id: Int): Flow<ChatModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModels(vararg items: ChatModel)

    @Query("DELETE FROM ChatModel")
    suspend fun clear()
}
