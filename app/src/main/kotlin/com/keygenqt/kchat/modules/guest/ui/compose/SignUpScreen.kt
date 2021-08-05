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
 
package com.keygenqt.kchat.modules.guest.ui.compose

import android.content.res.Configuration
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.keygenqt.kchat.modules.guest.ui.events.SignUpEvents
import com.keygenqt.kchat.modules.guest.ui.viewModels.GuestViewModel
import com.keygenqt.kchat.theme.KChatTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun SignUpScreen(
    viewModel: GuestViewModel,
    onNavigationEvent: (SignUpEvents) -> Unit = {},
) {
    val commonError: String? by viewModel.commonError.collectAsState()
    val loading: Boolean by viewModel.loading.collectAsState()

    SignUpBody(
        loading = loading,
        commonError = commonError,
        onNavigationEvent = onNavigationEvent,
    )
}

@Composable
fun SignUpBody(
    loading: Boolean = false,
    commonError: String? = null,
    onNavigationEvent: (SignUpEvents) -> Unit = {},
) {

}

@Preview("Light")
@Preview("Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSignUpBody() {
    KChatTheme {
        Surface {
            SignUpBody()
        }
    }
}