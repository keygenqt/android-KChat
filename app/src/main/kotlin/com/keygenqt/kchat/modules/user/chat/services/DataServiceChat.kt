package com.keygenqt.kchat.modules.user.chat.services

import androidx.paging.PagingSource
import com.keygenqt.kchat.base.AppSharedPreferences
import com.keygenqt.kchat.base.BaseDataService
import com.keygenqt.kchat.data.AppDatabase
import com.keygenqt.kchat.data.models.ChatModel
import kotlinx.coroutines.flow.Flow

class DataServiceChat(
    override val db: AppDatabase,
    override val preferences: AppSharedPreferences,
) : BaseDataService<DataServiceChat> {

    private val daoChatModel = db.daoChatModel()

    fun pagingListChats(): PagingSource<Int, ChatModel> {
        return daoChatModel.pagingSource()
    }

    suspend fun insertChats(models: List<ChatModel>) {
        daoChatModel.insertModels(models)
    }

    suspend fun clearChats() {
        daoChatModel.clear()
    }

    fun getChat(id: Int): Flow<ChatModel> {
        return daoChatModel.getModel(id)
    }

    suspend fun updateChats(models: List<ChatModel>) {
        daoChatModel.clear()
        daoChatModel.insertModels(models)
    }
}