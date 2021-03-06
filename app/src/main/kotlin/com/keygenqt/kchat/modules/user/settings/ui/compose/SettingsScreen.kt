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
 
package com.keygenqt.kchat.modules.user.settings.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keygenqt.kchat.R
import com.keygenqt.kchat.modules.common.ui.compose.MainScaffold
import com.keygenqt.kchat.modules.user.settings.ui.events.SettingsEvents
import com.keygenqt.kchat.modules.user.settings.ui.viewModels.SettingsViewModel
import com.keygenqt.kchat.theme.KChatTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onNavigationEvent: (SettingsEvents) -> Unit = {},
) {
    SettingsBody(
        onNavigationEvent = onNavigationEvent,
    )
}

@Composable
fun SettingsBody(
    onNavigationEvent: (SettingsEvents) -> Unit = {},
) {
    MainScaffold(
        title = stringResource(id = R.string.settings_title),
        navigationIconOnClick = { onNavigationEvent(SettingsEvents.NavigateBack) },
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
fun PreviewSettingsBody() {
    KChatTheme {
        Surface {
            SettingsBody()
        }
    }
}