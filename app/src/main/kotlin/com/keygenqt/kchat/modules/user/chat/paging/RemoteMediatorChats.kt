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
import timber.log.Timber

@ExperimentalPagingApi
class RemoteMediatorChats(
    private val data: DataServiceChat,
    private val repository: ApiServiceChat,
) : RemoteMediator<Int, ChatModel>() {

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
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem =
                        state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
                    lastItem.id
                }
            }

            val response = repository.getListChats(
                offset = offset ?: 0
            )

            response.success { models ->
                data.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        preferences.lastUpdateListChats = System.currentTimeMillis()
                        clearChats()
                    }
                    insertChats(models)
                }
            }.error { Timber.e(it) }

            MediatorResult.Success(
                endOfPaginationReached = response.isError || response.isEmpty
            )

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}