package com.keygenqt.kchat.modules.user.chat.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.keygenqt.kchat.base.error
import com.keygenqt.kchat.base.isEmpty
import com.keygenqt.kchat.base.isError
import com.keygenqt.kchat.base.success
import com.keygenqt.kchat.data.models.ChatModel
import com.keygenqt.kchat.modules.user.chat.services.ApiServiceChat
import com.keygenqt.kchat.modules.user.chat.services.DataServiceChat
import com.keygenqt.kchat.utils.ConstantsPaging.CACHE_TIMEOUT
import com.keygenqt.kchat.utils.ConstantsPaging.PAGE_LIMIT
import timber.log.Timber

@ExperimentalPagingApi
class ChatsRemoteMediator(
    private val data: DataServiceChat,
    private val apiService: ApiServiceChat,
) : RemoteMediator<Int, ChatModel>() {

    companion object {
        var key: Int? = null
    }

    override suspend fun initialize(): InitializeAction {
        return if (System.currentTimeMillis() - data.preferences.lastUpdateListChats >= CACHE_TIMEOUT) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ChatModel>,
    ): MediatorResult {
        return try {

            val offset = when (loadType) {
                LoadType.REFRESH -> {
                    key = null
                    key
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    key = (key ?: 0).plus(1)
                    key
                }
            }

            val response = apiService.getListChats(
                offset = (offset ?: 0) * PAGE_LIMIT
            )

            response.success { models ->
                data.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        preferences.lastUpdateListChats = System.currentTimeMillis()
                        clearChats()
                    }
                    insertChats(models)
                }
            }.error {
                Timber.e(it)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.isError || response.isEmpty
            )

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}