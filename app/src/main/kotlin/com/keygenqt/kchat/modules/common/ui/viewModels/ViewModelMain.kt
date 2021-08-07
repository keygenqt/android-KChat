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

package com.keygenqt.kchat.modules.common.ui.viewModels

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.keygenqt.kchat.BuildConfig
import com.keygenqt.kchat.base.AppSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewModelMain @Inject constructor(
    remoteConfig: FirebaseRemoteConfig,
    private val firebaseAuth: FirebaseAuth,
    private val analytics: FirebaseAnalytics,
    private val preferences: AppSharedPreferences,
) : ViewModel() {

    private val _isReady: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> get() = _isReady.asStateFlow()

    private val _isLogin: MutableStateFlow<Boolean> = MutableStateFlow(firebaseAuth.currentUser != null)
    val isLogin: StateFlow<Boolean> get() = _isLogin.asStateFlow()

    private val _showSnackBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showSnackBar: StateFlow<Boolean> get() = _showSnackBar.asStateFlow()

    private val _graph: MutableStateFlow<String> = MutableStateFlow("")
    val graph: StateFlow<String> get() = _graph.asStateFlow()

    private val _route: MutableStateFlow<String> = MutableStateFlow("")
    val route: StateFlow<String> get() = _route.asStateFlow()

    private val _showMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val showMessage: StateFlow<String?> get() = _showMessage.asStateFlow()

    init {
        viewModelScope.launch {
            // Hold a little splash
            delay(500)

            // Update token user
            preferences.token = firebaseAuth.currentUser?.let {
                firebaseAuth.currentUser?.getIdToken(false)?.await()?.token ?: ""
            } ?: ""

            // Update Remote Config
            remoteConfig.fetchAndActivate().await()

            // Show token
            Timber.d("User token -> ${preferences.token}")

            // Start app
            _isReady.value = true
        }
    }

    fun toggleSnackBar() {
        _showSnackBar.value = true
        viewModelScope.launch {
            delay(1500) // Loading second click
            _showSnackBar.value = false
        }
    }

    fun setCurrentRoute(graph: String, route: String) {
        if (route != _route.value || graph != _route.value) {
            // update data
            _graph.value = graph
            _route.value = route
            // sand analytics
            analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, Bundle().apply {
                putString(FirebaseAnalytics.Param.SCREEN_CLASS, graph)
                putString(FirebaseAnalytics.Param.SCREEN_NAME, route)
            })
        }
    }

    fun startUser() {
        firebaseAuth.currentUser?.let {
            _isLogin.value = true
        }
    }

    fun showMessage(text: String) {
        _showMessage.value = text
        // clear text messages after show
        viewModelScope.launch {
            delay(100); _showMessage.value = null
        }
    }

    fun logout() {
        firebaseAuth.signOut()
        _isLogin.value = false
    }

//    init {
//        viewModelScope.launch {
//            delay(2000) // slow internet
//            val item: UserResponses = httpClient.get("https://api.github.com/users/keygenqt")
//            _user.value = item.toModel()
//        }
//    }
//
//    var default: DefaultClientWebSocketSession? = null
//
//    fun connectChat() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                try {
//                    webSocketClient.webSocket(
//                        method = HttpMethod.Get,
//                        host = "192.168.1.68",
//                        port = 8080,
//                        path = "/chat"
//                    ) {
//                        Timber.e("Connection successfully!")
//                        _isReady.value = true
//                        default = this
//                        outputMessages()
//                    }
//                } catch (e: ConnectException) {
//                    Timber.e("Error while receiving: %s", e.localizedMessage)
//                }
//            }
//        }
//    }
//
//    fun closeChat() {
//        if (webSocketClient.isActive) webSocketClient.close()
//    }
//
//    fun sendMessage(text: String) {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                Timber.e(":!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
//                default!!.inputMessages(text)
//            }
//        }
//    }
}
