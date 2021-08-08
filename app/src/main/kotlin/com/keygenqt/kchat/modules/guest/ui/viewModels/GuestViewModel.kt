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

package com.keygenqt.kchat.modules.guest.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.keygenqt.kchat.base.AppSharedPreferences
import com.keygenqt.kchat.extensions.logging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class GuestViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val preferences: AppSharedPreferences,
) : ViewModel() {

    private val _commonError: MutableStateFlow<String?> = MutableStateFlow(null)
    val commonError: StateFlow<String?> get() = _commonError.asStateFlow()

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading.asStateFlow()

    fun signUp(email: String, password: String, success: () -> Unit) {
        clear()
        _loading.value = true
        viewModelScope.launch {
            try {
                // auth
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                // update token user
                preferences.token = firebaseAuth.currentUser?.let {
                    firebaseAuth.currentUser?.getIdToken(true)?.await()?.token ?: ""
                } ?: ""
                // done
                success.invoke()
            } catch (ex: Exception) {
                _commonError.value = ex.logging("Sign Up failed")
                _loading.value = false
            }
        }
    }

    fun login(email: String, password: String, success: () -> Unit) {
        clear()
        _loading.value = true
        viewModelScope.launch {
            try {
                // auth
                firebaseAuth.signInWithEmailAndPassword(email, password).await()
                // update token user
                preferences.token = firebaseAuth.currentUser?.let {
                    firebaseAuth.currentUser?.getIdToken(true)?.await()?.token ?: ""
                } ?: ""
                // done
                success.invoke()
            } catch (ex: Exception) {
                _commonError.value = ex.logging("Authentication failed")
                _loading.value = false
            }
        }
    }

    fun clear() {
        _commonError.value = null
        _loading.value = false
    }
}
