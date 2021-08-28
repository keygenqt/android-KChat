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
 
package com.keygenqt.kchat.modules.user.chat.ui.compose.viewChat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.paging.ExperimentalPagingApi
import com.keygenqt.kchat.modules.user.chat.ui.events.ViewChatEvents
import com.keygenqt.kchat.modules.user.chat.ui.viewModels.ChatViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ViewChatScreen(
    id: Int,
    viewModel: ChatViewModel,
    onEvent: (ViewChatEvents) -> Unit = {},
) {
    val model by viewModel.getModel(id).collectAsState(null)
    model?.let {
        ViewChatBody(
            model = it,
            onEvent = onEvent
        )
    }
}
