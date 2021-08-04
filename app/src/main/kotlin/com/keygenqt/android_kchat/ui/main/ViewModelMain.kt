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

package com.keygenqt.android_kchat.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keygenqt.android_kchat.base.RestHttpClient
import com.keygenqt.android_kchat.base.WebSocketClient
import com.keygenqt.android_kchat.data.mappers.toModel
import com.keygenqt.android_kchat.data.models.UserModel
import com.keygenqt.android_kchat.data.responses.UserResponses
import com.keygenqt.android_kchat.extensions.inputMessages
import com.keygenqt.android_kchat.extensions.outputMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class ViewModelMain @Inject constructor(
    @RestHttpClient httpClient: HttpClient,
    @WebSocketClient val webSocketClient: HttpClient
) : ViewModel() {

    private val _isReady: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> get() = _isReady.asStateFlow()

    private val _user: MutableStateFlow<UserModel?> = MutableStateFlow(null)
    val user: StateFlow<UserModel?> get() = _user.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000) // slow internet
            val item: UserResponses = httpClient.get("https://api.github.com/users/keygenqt")
            _user.value = item.toModel()
        }
    }

    var default: DefaultClientWebSocketSession? = null

    fun connectChat() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    webSocketClient.webSocket(
                        method = HttpMethod.Get,
                        host = "192.168.1.68",
                        port = 8080,
                        path = "/chat"
                    ) {
                        Timber.e("Connection successfully!")
                        _isReady.value = true
                        default = this
                        outputMessages()
                    }
                } catch (e: ConnectException) {
                    Timber.e("Error while receiving: %s", e.localizedMessage)
                }
            }
        }
    }

    fun closeChat() {
        if (webSocketClient.isActive) webSocketClient.close()
    }

    fun sendMessage(text: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Timber.e(":!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
                default!!.inputMessages(text)
            }
        }
    }
}
