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

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keygenqt.kchat.data.models.ChatModel
import com.keygenqt.kchat.data.mock.mockChatModel
import com.keygenqt.kchat.modules.user.chat.ui.events.ListChatsEvents
import com.keygenqt.kchat.theme.KChatTheme

@Composable
fun ListChatsScreenItem(
    index: Int = 0,
    item: ChatModel,
    onNavigationEvent: (ListChatsEvents) -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable(
                onClick = {
                    onNavigationEvent(ListChatsEvents.ToChatView(item.id))
                }
            ),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    brush = listOf(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF480048),
                                Color(0xFFC04848),
                            )
                        ),
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF283048),
                                Color(0xFF859398),
                            )
                        ),
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF232526),
                                Color(0xFF414345),
                            )
                        ),
                    )[index % 3]
                )
        ) {
            Column {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = 24.dp, bottom = 24.dp, start = 18.dp, end = 18.dp)
                )
            }
        }
    }
}

@Preview("Light")
@Preview("Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewListChatsScreenItem() {
    KChatTheme {
        Surface {
            ListChatsScreenItem(item = mockChatModel())
        }
    }
}

