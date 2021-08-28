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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.keygenqt.kchat.data.mock.*
import com.keygenqt.kchat.data.models.ChatModel
import com.keygenqt.kchat.modules.common.ui.compose.MainScaffold
import com.keygenqt.kchat.modules.user.chat.ui.events.ViewChatEvents
import kotlinx.coroutines.launch

@Composable
fun ViewChatBody(
    model: ChatModel,
    onEvent: (ViewChatEvents) -> Unit = {},
) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    MainScaffold(
        icon = Icons.Filled.ArrowBack,
        title = model.name,
        navigationIconOnClick = {
            onEvent(ViewChatEvents.NavigateBack)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Messages(
                messages = listOf(
                    mockMessageModel1(),
                    mockMessageModel2(),
                    mockMessageModel3(),
                    mockMessageModel4(),
                    mockMessageModel5(),
                    mockMessageModel6(),
                ),
                modifier = Modifier
                    .background(Color.Cyan)
                    .weight(1f),
                scrollState = scrollState
            )
            UserInput(
                onMessageSent = { content ->

                },
                resetScroll = {
                    scope.launch {
                        scrollState.scrollToItem(0)
                    }
                },
                // Use navigationBarsWithImePadding(), to move the input panel above both the
                // navigation bar, and on-screen keyboard (IME)
                modifier = Modifier.navigationBarsWithImePadding(),
            )
        }
    }
}
