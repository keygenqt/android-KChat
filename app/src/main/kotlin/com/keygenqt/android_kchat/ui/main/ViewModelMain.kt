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
import com.keygenqt.android_kchat.data.mappers.toModel
import com.keygenqt.android_kchat.data.models.UserModel
import com.keygenqt.android_kchat.data.responses.UserResponses
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewModelMain @Inject constructor(httpClient: HttpClient) : ViewModel() {

    private val _user: MutableStateFlow<UserModel?> = MutableStateFlow(null)
    val user: StateFlow<UserModel?> get() = _user.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000) // slow internet
            val item: UserResponses = httpClient.get("https://api.github.com/users/keygenqt")
            _user.value = item.toModel()
        }
    }
}
