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

package com.keygenqt.kchat.modules.user.chat.ui.compose

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.keygenqt.kchat.R
import com.keygenqt.kchat.base.LocalBaseViewModel
import com.keygenqt.kchat.data.models.ChatModel
import com.keygenqt.kchat.modules.common.ui.compose.CommonList
import com.keygenqt.kchat.modules.common.ui.compose.MainOptionalMenu
import com.keygenqt.kchat.modules.common.ui.compose.MainScaffold
import com.keygenqt.kchat.modules.user.chat.ui.events.ListChatsEvents
import com.keygenqt.kchat.modules.user.chat.ui.viewModels.ChatViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@Composable
fun ListChatsScreen(
    viewModel: ChatViewModel,
    onNavigationEvent: (ListChatsEvents) -> Unit = {},
) {
    val isShowSnackBar: Boolean by LocalBaseViewModel.current.showSnackBar.collectAsState()
    val items: LazyPagingItems<ChatModel> = viewModel.listChats.collectAsLazyPagingItems()
    val createChatSuccess: Boolean? by viewModel.createChatSuccess.collectAsState()

    Box {
        ListChatsBody(
            items = items,
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

@ExperimentalComposeUiApi
@Composable
fun ListChatsBody(
    createChatSuccess: Boolean?,
    items: LazyPagingItems<ChatModel>,
    onNavigationEvent: (ListChatsEvents) -> Unit = {},
) {
    val context = LocalContext.current
    val message = stringResource(id = R.string.list_chats_create_chat_successfully)
    val showDialogCreate = remember { mutableStateOf(false) }

    LaunchedEffect(createChatSuccess) {
        if (createChatSuccess == true) {
            showDialogCreate.value = false
            items.refresh()
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Box {
        MainScaffold(
            icon = null,
            label = stringResource(id = R.string.list_chats_title),
            contentFloatingActionButton = {
                Icon(Icons.Filled.Add, "Add")
            },
            floatingActionButtonOnClick = {
                showDialogCreate.value = true
            },
            actions = {
                MainOptionalMenu(
                    onSettings = {
                        onNavigationEvent(ListChatsEvents.ToSettings)
                    },
                    onLogout = {
                        onNavigationEvent(ListChatsEvents.Logout)
                    }
                )
            }
        ) {
            CommonList(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                items = items,
                paddingBottom = 24.dp,
                state = rememberSwipeRefreshState(items.loadState.refresh is LoadState.Loading)
            ) { index, item ->
                ListChatsScreenItem(
                    index = index,
                    item = item,
                    onNavigationEvent = onNavigationEvent
                )
            }
        }

        ListChatsScreenAddDialog(
            openDialog = showDialogCreate,
            createChatSuccess = createChatSuccess,
            onNavigationEvent = onNavigationEvent,
        )
    }
}

