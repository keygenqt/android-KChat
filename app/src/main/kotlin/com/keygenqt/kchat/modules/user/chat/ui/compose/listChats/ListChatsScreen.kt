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
 
package com.keygenqt.kchat.modules.user.chat.ui.compose.listChats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.keygenqt.kchat.R
import com.keygenqt.kchat.base.LocalBaseViewModel
import com.keygenqt.kchat.data.models.ChatModel
import com.keygenqt.kchat.modules.user.chat.ui.events.ListChatsEvents
import com.keygenqt.kchat.modules.user.chat.ui.viewModels.ChatViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
@Composable
fun ListChatsScreen(
    viewModel: ChatViewModel,
    onNavigationEvent: (ListChatsEvents) -> Unit = {},
) {
    val search: String? by viewModel.search.collectAsState()
    val isShowSnackBar: Boolean by LocalBaseViewModel.current.showSnackBar.collectAsState()
    val items: LazyPagingItems<ChatModel> = viewModel.listChats.collectAsLazyPagingItems()
    val searchItems: LazyPagingItems<ChatModel> = viewModel.searchListChats.collectAsLazyPagingItems()
    val createChatSuccess: Boolean? by viewModel.createChatSuccess.collectAsState()

    Box {
        ListChatsBody(
            items = items,
            search = search,
            searchItems = searchItems,
            createChatSuccess = createChatSuccess,
            onNavigationEvent = onNavigationEvent,
        )
        if (isShowSnackBar) {
            Snackbar(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart)
            ) {
                Text(text = stringResource(id = R.string.common_exit))
            }
        }
    }
}


