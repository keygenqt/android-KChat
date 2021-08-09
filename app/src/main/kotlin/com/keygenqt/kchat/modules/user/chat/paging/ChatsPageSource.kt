package com.keygenqt.kchat.modules.user.chat.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.keygenqt.kchat.base.pagingSucceeded
import com.keygenqt.kchat.data.models.ChatModel
import com.keygenqt.kchat.modules.user.chat.services.ApiServiceChat

class ChatsPageSource(
    private val search: String?,
    private val repository: ApiServiceChat,
) : PagingSource<Int, ChatModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChatModel> {
        val offset = params.key ?: 0
        return repository.getListChats(search = search, offset = offset).pagingSucceeded { data ->
            LoadResult.Page(
                data = data,
                prevKey = if (offset == 0) null else offset,
                nextKey = if (data.isEmpty()) null else data.last().id
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ChatModel>): Int {
        return 0
    }
}