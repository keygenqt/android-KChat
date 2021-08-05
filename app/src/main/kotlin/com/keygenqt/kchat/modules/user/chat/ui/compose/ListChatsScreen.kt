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

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keygenqt.kchat.R
import com.keygenqt.kchat.modules.common.ui.compose.MainOptionalMenu
import com.keygenqt.kchat.modules.common.ui.compose.MainScaffold
import com.keygenqt.kchat.modules.user.chat.ui.events.ListChatsEvents
import com.keygenqt.kchat.modules.user.chat.ui.viewModels.ChatViewModel
import com.keygenqt.kchat.theme.KChatTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun ListChatsScreen(
    viewModel: ChatViewModel,
    onNavigationEvent: (ListChatsEvents) -> Unit = {},
) {
    ListChatsBody(
        onNavigationEvent = onNavigationEvent,
    )
}

@Composable
fun ListChatsBody(
    onNavigationEvent: (ListChatsEvents) -> Unit = {},
) {
    MainScaffold(
        icon = null,
        label = stringResource(id = R.string.list_chats_title),
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
    ) { innerPadding ->

        val modifier = Modifier.padding(innerPadding)
        val padding = 16.dp

        Column(
            modifier = modifier
                .padding(start = padding, end = padding)
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {

        }
    }
}

@Preview("Light")
@Preview("Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewListChatsBody() {
    KChatTheme {
        Surface {
            ListChatsBody()
        }
    }
}

