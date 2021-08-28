/*
 * Copyright 2021 Vitaliy Zarubin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package com.keygenqt.kchat.modules.user.chat.services

import androidx.paging.PagingSource
import com.keygenqt.kchat.base.AppSharedPreferences
import com.keygenqt.kchat.base.BaseDataService
import com.keygenqt.kchat.data.AppDatabase
import com.keygenqt.kchat.data.models.ChatModel
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class DataServiceChat(
    override val db: AppDatabase,
    override val preferences: AppSharedPreferences,
) : BaseDataService<DataServiceChat> {

    private val daoChatModel = db.daoChatModel()

    fun pagingListChats(): PagingSource<Int, ChatModel> {
        return daoChatModel.pagingSource()
    }

    suspend fun insertChats(models: List<ChatModel>) {
        daoChatModel.insertModels(*models.reversed().toTypedArray())
    }

    suspend fun clearChats() {
        daoChatModel.clear()
    }

    fun getChat(id: Int): Flow<ChatModel?> {
        return daoChatModel.getModel(id)
    }

    suspend fun updateChats(models: List<ChatModel>) {
        daoChatModel.clear()
        daoChatModel.insertModels(*models.toTypedArray())
    }
}