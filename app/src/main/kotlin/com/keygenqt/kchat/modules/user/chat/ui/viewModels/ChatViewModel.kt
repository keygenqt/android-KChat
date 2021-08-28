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
 
package com.keygenqt.kchat.modules.user.chat.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.keygenqt.kchat.base.done
import com.keygenqt.kchat.base.error
import com.keygenqt.kchat.base.success
import com.keygenqt.kchat.data.models.ChatModel
import com.keygenqt.kchat.modules.user.chat.paging.ChatsPageSource
import com.keygenqt.kchat.modules.user.chat.paging.ChatsRemoteMediator
import com.keygenqt.kchat.modules.user.chat.services.ApiServiceChat
import com.keygenqt.kchat.modules.user.chat.services.DataServiceChat
import com.keygenqt.kchat.utils.ConstantsPaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val apiService: ApiServiceChat,
    private val dataService: DataServiceChat,
) : ViewModel() {

    private val _createChatSuccess: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val createChatSuccess: StateFlow<Boolean?> get() = _createChatSuccess.asStateFlow()

    private val _search: MutableStateFlow<String?> = MutableStateFlow(null)
    val search = _search.asStateFlow()

    val searchListChats: Flow<PagingData<ChatModel>> = Pager(PagingConfig(pageSize = ConstantsPaging.PAGE_LIMIT)) {
        ChatsPageSource(_search.value, apiService)
    }.flow.cachedIn(viewModelScope)

    @OptIn(ExperimentalPagingApi::class)
    val listChats: Flow<PagingData<ChatModel>> = Pager(
        config = PagingConfig(pageSize = ConstantsPaging.PAGE_LIMIT),
        remoteMediator = ChatsRemoteMediator(dataService, apiService)
    ) {
        dataService.pagingListChats()
    }.flow

    fun search(search: String?) {
        _search.value = search
    }

    fun createChat(name: String) {
        viewModelScope.launch {
            apiService.createChat(name)
                .success {
                    _createChatSuccess.value = true
                }
                .error {
                    _createChatSuccess.value = false
                    Timber.e(it)
                }
                .done {
                    delay(50)
                    _createChatSuccess.value = null
                }
        }
    }

    fun getModel(id: Int): Flow<ChatModel?> {
        return dataService.getChat(id).distinctUntilChanged()
    }
}
