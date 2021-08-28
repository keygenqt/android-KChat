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
 
package com.keygenqt.kchat.modules.user.chat.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.keygenqt.kchat.base.pagingSucceeded
import com.keygenqt.kchat.data.models.ChatModel
import com.keygenqt.kchat.modules.user.chat.services.ApiServiceChat
import com.keygenqt.kchat.utils.ConstantsPaging

class ChatsPageSource(
    private val search: String?,
    private val repository: ApiServiceChat,
) : PagingSource<Int, ChatModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChatModel> {
        val offset = params.key ?: 0
        if (search == null) {
            return LoadResult.Page(
                data = listOf(),
                prevKey = null,
                nextKey = null,
            )
        }
        return repository.getListChats(search = search, offset = offset).pagingSucceeded { data ->
            LoadResult.Page(
                data = data,
                prevKey = if (offset == 0) null else offset,
                nextKey = if (data.isEmpty()) null else offset + ConstantsPaging.PAGE_LIMIT
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ChatModel>): Int {
        return 0
    }
}